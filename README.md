# Cavatappi 網路訂餐系統

本專案為使用 Java Spring Boot 開發的微服務架構練習，模擬線上訂餐平台的後端系統設計流程，包含了服務拆分、跨服務通訊、安全機制、資料庫隔離等概念。

> 專案採多分支開發，請參考 `dev` 分支（目前主要開發分支），而非 `main`。

---

## 技術與架構

1. **Spring Security + JWT Filter Chain**  
   採用 Stateless 架構，統一由 Gateway 驗證並解析 JWT Token，登入者資訊（如 UUID、角色），透過 HTTP Header 傳遞至各個微服務。

2. **Spring Cloud Gateway（MVC 模式）**  
   本專案規模較小，不使用 WebFlux。

3. **PostgreSQL + Docker 多資料庫容器**  
   每個微服務使用獨立資料庫，並透過 Docker 容器進行隔離與部署。

4. **MonoRepo 版本管理**  
   採用單一 Git 統一管理所有微服務與前端程式碼。

---

## 微服務概覽

1. **gateway-service**  
   負責統一轉發外部請求至各微服務，並實作 JWT 安全機制（Token 驗證、過期處理、角色授權等）。

2. **user-service**  
   管理使用者註冊與登入：
   - 支援快速註冊與完整註冊。
   - 完整註冊包含 OTP 驗證機制，系統會寄送驗證碼至新用戶信箱。
   - 密碼採用 BCrypt 加密儲存。
   - 實作 UserDetails，將使用者資訊裝入 JWT。
   - 已預留 OAuth 第三方服務登入功能的資料表結構，之後可擴充 Google、Apple 登入等。

3. **menu-service**  
   管理餐點資訊：
   - 實作的重點在資料表設計與 DTO 資料傳遞封裝。
   - 基本 CRUD 操作。
   - 支援管理者更新供應狀態（如停售、缺貨等）。

4. **order-service**  
   處理使用者下單流程，尚未實作。

---

## TODO（開發中）

> 以下項目正在開發或規劃中，會持續更新併入於 dev 分支：

- 實作 OAuth 第三方登入（Google、Apple）
- 餐點搜尋
- 訂單狀態管理（待接單、製作中、已完成）
- 客製購物車邏輯（支援加價購、套餐）
- 實作第三方付款功能。
- 前端頁面初步整合（Vue 3 + Pinia）

> 以下是不一定會實作的功能：

- Kafka
- Elastic Search

---
