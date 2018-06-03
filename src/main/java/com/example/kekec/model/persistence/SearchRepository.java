package com.example.kekec.model.persistence;

import java.util.List;

/**
 * @author Riste Stojanov
 */
public interface SearchRepository {

  <T> List<T> searchKeyword(Class<T> entityClass, String text, String... fields);

  <T> List<T> searchPhrase(Class<T> entityClass, String text, String... fields);
}
