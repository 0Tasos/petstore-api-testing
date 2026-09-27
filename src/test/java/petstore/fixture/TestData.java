package petstore.fixture;

import lombok.NoArgsConstructor;
import petstore.dto.Category;
import petstore.dto.Order;
import petstore.dto.Pet;
import petstore.dto.Tag;
import petstore.dto.User;

import java.util.Random;

@NoArgsConstructor
public final class TestData {
    public static final String DOG_NAME = "dog";
    public static final String DOG_STATUS = "available";
    public static final String PHOTO_URL = "https://example.com/dog.jpg";
    public static final String CATEGORY_NAME = "Dogs";
    public static final String TAG_NAME = "friendly";
    public static final int CATEGORY_ID = 1;

    public static final long PET_ID = 1L;
    public static final int QUANTITY = 2;
    public static final String ORDER_STATUS = "placed";
    public static final boolean ORDER_COMPLETE = true;

    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Smith";
    public static final String EMAIL = "john.smith@example.com";
    public static final String PASSWORD = "P@ssw0rd";
    public static final String PHONE = "1234567890";
    public static final int USER_STATUS = 1;

    private static final Random RANDOM = new Random();

    private static long randomId() {
        return RANDOM.nextLong(1, 1_000_000_000L);
    }

    public static Pet.PetBuilder pet(String name, String status) {
        return Pet.builder()
                .id(randomId())
                .name(name)
                .status(status);
    }

    public static Category category() {
        return Category.builder()
                .id(1)
                .name("Dogs")
                .build();
    }

    public static Tag tag() {
        return Tag.builder()
                .id(1)
                .name(TAG_NAME)
                .build();
    }

    public static Order.OrderBuilder order() {
        return Order.builder()
                .id(randomId())
                .petId(PET_ID)
                .quantity(QUANTITY)
                .status(ORDER_STATUS)
                .complete(ORDER_COMPLETE);
    }

    public static User.UserBuilder user() {
        return User.builder()
                .id(randomId())
                .username("user" + RANDOM.nextInt(1_000_000))
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .phone(PHONE)
                .userStatus(USER_STATUS);
    }
}