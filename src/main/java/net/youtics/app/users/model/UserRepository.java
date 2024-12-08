package net.youtics.app.users.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import net.youtics.app.users.utils.Conexion;

public class UserRepository implements UserInterface {

    private List<User> database = new ArrayList<>();
    private final BehaviorSubject<List<User>> listBehaviorSubject = BehaviorSubject.createDefault(new ArrayList<>());

    @Override
    public List<User> findAll() {
        loadUsersFromDB();
        return database = this.findAllDB();
    }

    public void loadUsersFromDB() {
        List<User> usuarios = this.findAllDB();
        database.clear(); // Limpiamos la lista existente
        database.addAll(usuarios); // Agregamos los nuevos elementos
        listBehaviorSubject.onNext(new ArrayList<>(database)); // Emitimos la lista actualizada al BehaviorSubject
    }


    public List<User> findAllDB() {
        String sql = "SELECT * FROM users";
        List<User> usuarios = new ArrayList<>();

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User usuario = new User();
                usuario.setId(rs.getLong("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public Optional<User> add(User user) {
        this.saveDB(user);
        this.loadUsersFromDB();
        return Optional.of(user);
    }

    public void saveDB(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Optional<User> findById(Long id) {
        Optional<User> ok = database
                .stream()
                .filter(user -> user.getId().equals(id))
                .findAny();
        if(ok.isPresent()){
            return ok;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(Long id) {
        Optional<User> ok = this.findById(id);
        if (ok.isPresent()){
            deleteDB(ok.get().getId());
            database.remove(ok.get());
            listBehaviorSubject.onNext(new ArrayList<>(database));
        }
        
        return ok;
    }

    public void deleteDB(Long id){
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection con = Conexion.conectar()){

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> update(Long id, User user) {
        if (id == null || user == null) {
            throw new IllegalArgumentException("El ID y el usuario no pueden ser nulos");
        }
        Optional<User> ok = this.findById(id);

        if(ok.isPresent()){
            Integer index = database.indexOf(ok.get());
            database.set(index, user);
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public BehaviorSubject<List<User>> getListBehaviorSubject() {
        return listBehaviorSubject;
    }
}
