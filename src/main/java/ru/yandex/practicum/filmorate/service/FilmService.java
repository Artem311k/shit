package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final GenreService genreService;

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film getById(Long id) {
        return filmStorage.getById(id);
    }

    public Film add(Film film) {
        Film receivedFilm = filmStorage.add(film);
        if (film.getGenres() != null) {
            genreService.updateForFilm(receivedFilm.getId(), film.getGenres());
        }
        return receivedFilm;
    }

    public Film update(Film film) {
        if (film.getGenres() != null) {
            genreService.updateForFilm(film.getId(), film.getGenres());
        }
        return filmStorage.update(film);
    }

    public void addLike(Long id, Long userId) throws NotFoundException {
        Film film = filmStorage.getById(id);
        filmStorage.addLike(id, userId);
    }

    public void removeLike(Long id, Long userId) throws NotFoundException {
        Film film = filmStorage.getById(id);
        if (!filmStorage.hasLikeFromUser(id, userId)) {
            throw new NotFoundException("Лайк пользователя " + userId + " фильму с id=" + id + " не найден");
        }
        filmStorage.removeLike(id, userId);
    }

    public List<Film> getTop(Integer count) {
        return filmStorage.getTop(count);
    }



}

