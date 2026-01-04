#!/bin/bash
set -e

# プロジェクトルートに移動（スクリプトがどこから実行されても大丈夫なように）
cd "$(dirname "$0")/../.."

echo "Starting Spring Boot application..."
./gradlew bootRun &
APP_PID=$!

# アプリケーション終了のためのトラップを設定
cleanup() {
    echo "Stopping application (PID: $APP_PID)..."
    kill $APP_PID
}
trap cleanup EXIT

echo "Waiting for the application to be ready..."
# ヘルスチェックで起動待機
MAX_RETRIES=60
RETRY_COUNT=0
URL="http://localhost:8080/actuator/health"

while [ $RETRY_COUNT -lt $MAX_RETRIES ]; do
    # ヘルスチェックもリトライ
    HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$URL" || echo "000")
    if [ "$HTTP_STATUS" == "200" ]; then
        echo "Application is up!"
        break
    else
        echo "Waiting for application... (Status: $HTTP_STATUS, Retry: $((RETRY_COUNT+1))/$MAX_RETRIES)"
    fi
    sleep 5
    RETRY_COUNT=$((RETRY_COUNT+1))
done

if [ $RETRY_COUNT -eq $MAX_RETRIES ]; then
    echo "Application failed to start in time."
    exit 1
fi

# OpenAPI YAML をダウンロード
echo "Downloading OpenAPI YAML..."
# -f でエラー時に失敗させる
curl -v -f "http://localhost:8080/v3/api-docs.yaml" -o doc/api/openapi.yaml || { echo "Failed to download OpenAPI YAML"; exit 1; }
echo "OpenAPI YAML saved to doc/api/openapi.yaml"
# ファイルサイズ確認
ls -l doc/api/openapi.yaml

# HTMLを生成
echo "Generating HTML documentation..."
./gradlew openApiGenerate


echo "Documentation generated successfully in doc/api/html"
