# sample-boot-app
Spring Bootのサンプルアプリケーション

## 開発環境でのメトリクス確認 (Prometheus)

Prometheusを使用してアプリケーションのメトリクスを確認できます。

1. **Dockerコンテナの起動**
   ```bash
   docker compose up -d
   ```
   これにより、PostgreSQLとPrometheusが起動します。

2. **アプリケーションの起動**
   ```bash
   ./gradlew bootRun
   ```

3. **Prometheusへのアクセス**
   ブラウザで以下のURLにアクセスしてください。
   [http://localhost:9090](http://localhost:9090)

4. **メトリクスの確認**
   - **Status > Targets** メニューで、`spring-boot-app` の State が `UP` になっていることを確認してください。
   - **Graph** タブで、Expressionにメトリクス名（例: `http_server_requests_seconds_count`）を入力して `Execute` を押すと、グラフや値を確認できます。
