package teachhub.com.TeachHub.core;

public record ApiResponse<T>(
        String status,
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(CNT.API.STATUS.SUCCESS, "", data);
    }
    public static <T> ApiResponse<T> custom(String status, T data) {
        return new ApiResponse<>(status, "", data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(CNT.API.STATUS.ERROR, message, null);
    }
}
