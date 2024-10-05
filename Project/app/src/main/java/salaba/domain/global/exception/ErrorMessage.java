package salaba.domain.global.exception;

public class ErrorMessage {
    public static String entityNotFound(Class<?> entity, Long id) {
        String className = entity.getName();
        return className + " is not found with id: " + id;
    }

    public static String entityNotFound(Class<?> entity, String condition) {
        String className = entity.getName();
        return className + " is not found with: " + condition;
    }


}
