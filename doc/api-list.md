# ECサイト API一覧

各コンテキストが提供する主要なAPIの一覧です。

## 1. 商品コンテキスト (Product Context)
Base Path: `/api/products`

| Method | Path | Summary | Description |
| :--- | :--- | :--- | :--- |
| GET | `/` | 商品検索 | 条件（キーワード、カテゴリ等）に一致する商品を一覧で返す。ページネーション対応。 |
| GET | `/{productId}` | 商品詳細取得 | 指定されたIDの商品の詳細情報を返す。 |
| GET | `/categories` | カテゴリ一覧取得 | 商品カテゴリの階層構造を返す。 |

## 2. 注文コンテキスト (Order Context)
Base Path: `/api`

### カート (Cart)
| Method | Path | Summary | Description |
| :--- | :--- | :--- | :--- |
| GET | `/cart` | カート参照 | 現在のユーザーのカート内の商品一覧を返す。 |
| POST | `/cart/items` | カート追加 | カートに商品を追加する。 |
| PUT | `/cart/items/{itemId}` | 数量変更 | カート内の特定商品の数量を変更する。 |
| DELETE | `/cart/items/{itemId}` | カート削除 | カートから特定の商品を削除する。 |

### 注文 (Orders)
| Method | Path | Summary | Description |
| :--- | :--- | :--- | :--- |
| POST | `/orders` | 注文確定 | カートの内容で注文を作成し、在庫を引き当てて確定する。 |
| GET | `/orders` | 注文履歴 | ユーザーの過去の注文履歴を一覧で返す。 |
| GET | `/orders/{orderId}` | 注文詳細 | 指定された注文の詳細情報を返す。 |

## 3. 在庫コンテキスト (Inventory Context)
Base Path: `/api/inventory` (内部APIまたは管理者向け)

| Method | Path | Summary | Description |
| :--- | :--- | :--- | :--- |
| GET | `/products/{productId}` | 在庫数確認 | 指定された商品の現在の在庫数を返す。 |
| POST | `/reservations` | 在庫引き当て | 注文時に在庫を確保する。 |

## 4. 会員コンテキスト (User Context)
Base Path: `/api/users`

| Method | Path | Summary | Description |
| :--- | :--- | :--- | :--- |
| POST | `/` | 会員登録 | 新規ユーザーを作成する。 |
| POST | `/login` | ログイン | 認証を行い、トークンを発行する。 |
| GET | `/me` | 会員情報取得 | ログイン中のユーザーのプロフィール情報を返す。 |
| PUT | `/me` | 会員情報更新 | プロフィール情報を更新する。 |
