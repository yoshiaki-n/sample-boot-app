# コンテキストマップ (Context Map)

各コンテキストの関係性と依存方向を定義します。

## 関係図

```mermaid
graph TD
    %% Context Definitions
    Order[注文コンテキスト<br/>(Order Context)]
    Product[商品コンテキスト<br/>(Product Context)]
    Inventory[在庫コンテキスト<br/>(Inventory Context)]
    User[会員コンテキスト<br/>(User Context)]

    %% Relationships
    Order -->|商品情報の参照<br/>ID参照| Product
    Order -->|在庫引き当て要求| Inventory
    Order -->|注文者情報の参照<br/>ID参照| User
    
    %% Styles
    classDef context fill:#f9f,stroke:#333,stroke-width:2px;
    class Order,Product,Inventory,User context;
```

## 関係性の説明

### 注文 (Order) -> 商品 (Product)
- **関係**: 注文は商品情報（商品名、価格など）を必要とします。
- **実装方針**: 注文コンテキストは商品IDのみを保持し、必要な情報は商品コンテキストのサービス/API経由で取得、または注文確定時にスナップショットとして保存します。

### 注文 (Order) -> 在庫 (Inventory)
- **関係**: 注文作成時に在庫の引き当てが必要です。
- **実装方針**: 注文確定プロセスの一部として、同期的に在庫コンテキストの引き当て処理を呼び出します。

### 注文 (Order) -> 会員 (User)
- **関係**: 注文は誰によって行われたか（会員ID）を紐付けます。
- **実装方針**: 注文コンテキストは会員IDを保持します。配送先情報などは注文時にスナップショットとして保存する場合があります。
