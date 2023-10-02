package com.example.dataanalyze.services.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Утилитарный класс для валидации аргументов командной строки, используемых в приложении.
 * Класс предоставляет методы для проверки корректности переданных аргументов
 */
public class ValidationUtil {

    /**
     * Проверяет и валидирует аргументы командной строки.
     *
     * @param args Аргументы командной строки.
     * @throws IllegalArgumentException Если аргументы не прошли валидацию.
     */

    public static void validateCommandLineArgs(String[] args) {
        validateCountArgs(args);
        validateOperationType(args[0]);
        validateInputFilePath(args[1]);
        validateOutputFilePath(args[2]);
    }

    private static void validateCountArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(Errors.ERROR_COUNT_ARGS.getMessage());
        }
    }

    private static void validateOperationType(String operationType) {
        if (!operationType.equals(Errors.TYPE_OPERATION_SEARCH.getMessage()) && !operationType.equals(Errors.TYPE_OPERATION_STAT.getMessage())) {
            throw new IllegalArgumentException(Errors.ERROR_OPERATION_TYPE.getMessage() + ": " + operationType);
        }
    }

    private static boolean isFileValid(String filePath, boolean mustExist) {
        Path path = Paths.get(filePath);
        if (mustExist) {
            return !Files.exists(path) || Files.isDirectory(path);
        } else {
            Path parent = path.getParent();
            return parent == null || !Files.isDirectory(parent);
        }
    }

    private static void validateInputFilePath(String filePath) {
        if (isFileValid(filePath, true)) {
            throw new IllegalArgumentException(Errors.ERROR_INCORRECT_INPUT_FILE.getMessage() + filePath);
        }
    }

    private static void validateOutputFilePath(String filePath) {
        if (isFileValid(filePath, false)) {
            throw new IllegalArgumentException(Errors.ERROR_INCORRECT_OUTPUT_FILEPATH.getMessage() + filePath);
        }
    }

    @Getter
    @AllArgsConstructor
    private enum Errors {
        TYPE_OPERATION_SEARCH("search"),
        TYPE_OPERATION_STAT("stat"),
        ERROR_COUNT_ARGS("Некорректное количество аргументов! Пожалуйста, укажите три аргумента: тип операции, входной файл, выходной файл."),
        ERROR_OPERATION_TYPE("Неизвестный тип операции! Поддерживаемые типы операций: 'search' (Поиск) и 'stat' (Статистика)."),
        ERROR_INCORRECT_INPUT_FILE("Входной файл не существует! Пожалуйста, убедитесь, что входной файл существует по указанному пути."),
        ERROR_INCORRECT_OUTPUT_FILEPATH("Некорректный путь к выходному файлу! Пожалуйста, укажите корректный путь для выходного файла.");

        private final String message;

    }

}
