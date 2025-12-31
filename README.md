# sample-boot-app

Spring Boot サンプルアプリケーション

## プロジェクト概要

このプロジェクトは、Spring Bootを使用したWebアプリケーションのバックエンド実装サンプルです。
ドメイン駆動設計 (DDD) とオニオンアーキテクチャを採用し、堅牢で保守性の高いコードベースを目指しています。

### 技術スタック

*   **言語**: Java 17
*   **フレームワーク**: Spring Boot 3.x
*   **ビルドツール**: Gradle
*   **アーキテクチャ**: Onion Architecture, DDD
*   **データベース**: PostgreSQL
*   **マイグレーション**: Flyway
*   **ORM**: MyBatis

## 環境構築 (Getting Started)

開発を始めるための手順です。

### 前提条件

*   Java 17 以上がインストールされていること
*   Docker および Docker Compose がインストールされていること

### 起動手順

1.  **データベースと監視ツールの起動**
    ローカル開発環境用のデータベース (PostgreSQL) と監視ツール (Prometheus) を起動します。

    ```bash
    docker compose up -d
    ```

2.  **アプリケーションの起動**
    Gradleラッパーを使用してアプリケーションを起動します。

    ```bash
    ./gradlew bootRun
    ```
    
    起動後、 `http://localhost:8080` でアクセス可能です。

## 開発ガイド

日々の開発で使用する主なGradleタスクの説明です。

### テストの実行

このプロジェクトでは、テストの粒度や目的に応じて複数のタスク定義しています。

| タスク名 | 説明 | 実行コマンド |
| :--- | :--- | :--- |
| `test` | **単体テスト**を実行します。外部依存（DBなど）を含まないテストです。 | `./gradlew test` |
| `integrationTest` | **統合テスト**を実行します。DBなどの外部リソースと接続して動作確認を行います。 | `./gradlew integrationTest` |
| `fitnessTest` | **適応度関数テスト (ArchUnit)** を実行します。パッケージ依存関係などのアーキテクチャルールを守れているかチェックします。 | `./gradlew fitnessTest` |
| `allTest` | 上記すべてのテストを実行し、統合されたカバレッジレポートを生成します。 | `./gradlew allTest` |

### コード品質チェック

コードの品質を保つために、以下の静的解析ツールを導入しています。

*   **Spotless**: コードフォーマットの統一
*   **Checkstyle**: コーディング規約のチェック (Google Checks準拠)
*   **SpotBugs**: バグの可能性の検知
*   **PMD**: 不要なコードや非効率な記述の検知

これらのチェックを一括で行うには、以下のコマンドを実行します。

```bash
./gradlew check
```

コミット前には必ずこのコマンドを実行し、エラーがないことを確認してください。

### コードフォーマット

コードのフォーマットを自動修正するには、以下のコマンドを実行します。

```bash
./gradlew spotlessApply
```

## 運用管理・モニタリング

### メトリクス確認 (Prometheus)

Prometheusを使用してアプリケーションの稼働状況を確認できます。

1.  上記「起動手順」に従い、`docker compose up -d` と `./gradlew bootRun` を実行してください。
2.  ブラウザで [http://localhost:9090](http://localhost:9090) にアクセスします。
3.  **Status > Targets** メニューで、`spring-boot-app` の State が `UP` になっていることを確認してください。
4.  **Graph** タブでメトリクス名（例: `http_server_requests_seconds_count`）を入力して `Execute` を押すと、グラフや値を確認できます。

## ディレクトリ構成

主なディレクトリの役割です。

*   `src/main/java`: プロダクションコード
    *   `com.example.samplebootapp`
        *   `domain`: ドメイン層 (ビジネスロジック、エンティティ)
        *   `usecase`: ユースケース層 (アプリケーションロジック)
        *   `infrastructure`: インフラストラクチャ層 (DBアクセス、外部API連携)
        *   `presentation`: プレゼンテーション層 (Web API コントローラー)
*   `src/test/java`: 単体テストコード
*   `src/integrationTest/java`: 統合テストコード
*   `src/fitnessTest/java`: アーキテクチャテストコード
*   `doc`: プロジェクトのドキュメント (API仕様、アーキテクチャ詳細など)
*   `docker`: Docker関連の設定ファイル
