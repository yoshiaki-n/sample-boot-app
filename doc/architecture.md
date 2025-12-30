# アーキテクチャ構成 (Architecture)

モジュラーモノリス構成を採用し、パッケージ構成を以下のように定義します。

## パッケージ構成

トップレベルパッケージ: `com.example.samplebootapp`

```
com.example.samplebootapp
├── product          <-- 商品コンテキスト
│   ├── application
│   ├── domain
│   ├── infrastructure
│   └── presentation
├── order            <-- 注文コンテキスト
├── inventory        <-- 在庫コンテキスト
├── user             <-- 会員コンテキスト
└── shared           <-- 共有カーネル (基底クラスなど)
```

## レイヤーアーキテクチャ (各コンテキスト内部)

各コンテキスト内部では、オニオンアーキテクチャ/クリーンアーキテクチャに基づいたレイヤー構造を採用します。

- **presentation**: コントローラー、API定義
- **application**: アプリケーションサービス、ユースケース
- **domain**: ドメインモデル (Aggregate, Entity, ValueObject), ドメインサービス
- **infrastructure**: リポジトリ実装、外部APIクライアント
