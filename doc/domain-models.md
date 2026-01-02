# ドメインモデル (Domain Models)

各コンテキストのドメインモデルを定義します。データだけでなく振る舞い（メソッド）を持たせ、ドメイン貧血症を防ぎます。

## 凡例
- `<<Aggregate Root>>`: 集約ルート。整合性の境界。
- `<<Entity>>`: 識別子を持つオブジェクト。
- `<<Value Object>>`: 値によって識別されるオブジェクト。

## 1. 商品コンテキスト (Product Context)

```mermaid
classDiagram
    class Product {
        <<Aggregate Root>>
        +ProductId id
        +String name
        +String description
        +Price price
        +CategoryId categoryId
        +changePrice(newPrice)
        +updateDetail(name, description)
    }

    class Category {
        <<Entity>>
        +CategoryId id
        +String name
        +CategoryId parentId
        +rename(newName)
    }

    Category --> Category : parentId

    Product --> Category : categoryId
```

## 2. 注文コンテキスト (Order Context)

```mermaid
classDiagram
    class Order {
        <<Aggregate Root>>
        +OrderId id
        +UserId userId
        +OrderStatus status
        +ShippingAddress address
        +List~OrderItem~ items
        +PaymentMethod paymentMethod
        +DateTime orderedAt
        +addItem(product, quantity, price)
        +removeItem(productId)
        +complete()
        +cancel()
    }

    class OrderItem {
        <<Entity>>
        +ProductId productId
        +String productName
        +Price price
        +Quantity quantity
        +calculateSubtotal() Price
    }

    class Cart {
        <<Aggregate Root>>
        +CartId id
        +UserId userId
        +List~CartItem~ items
        +addItem(productId, quantity)
        +removeItem(productId)
        +changeQuantity(productId, quantity)
        +clear()
    }

    class CartItem {
        <<Value Object>>
        +ProductId productId
        +Quantity quantity
        +add(quantity)
    }

    Order "1" *-- "n" OrderItem : contains
    Cart "1" *-- "n" CartItem : contains
```

## 3. 在庫コンテキスト (Inventory Context)

```mermaid
classDiagram
    class Inventory {
        <<Aggregate Root>>
        +ProductId productId
        +Quantity quantity
        +Location location
        +allocate(quantity) void
        +replenish(quantity) void
        +isAvailable(quantity) boolean
    }
```

## 4. 会員コンテキスト (User Context)

```mermaid
classDiagram
    class User {
        <<Aggregate Root>>
        +UserId id
        +String email
        +String passwordHash
        +Profile profile
        +changePassword(newPassword)
        +updateProfile(profile)
        +delete()
        +isAuthenticated(password) boolean
    }

    class Profile {
        <<Value Object>>
        +String firstName
        +String lastName
        +PhoneNumber phoneNumber
        +Address address
        +fullName() String
    }

    User *-- Profile
```
