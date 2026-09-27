package petstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Urls {
    PET("/pet"),
    PET_BY_ID("/pet/{petId}"),
    PET_FIND_BY_STATUS("/pet/findByStatus"),

    STORE_ORDER("/store/order"),
    STORE_ORDER_BY_ID("/store/order/{orderId}"),
    STORE_INVENTORY("/store/inventory"),

    USER("/user"),
    USER_BY_USERNAME("/user/{username}");

    private final String path;
}