# Product Service

상품 및 상품 옵션을 관리하는 REST API 서비스입니다.

## API 목록

### 상품 API (`/api/v1/products`)

| Method | Endpoint | Description | Request | Response |
|--------|----------|-------------|----------|-----------|
| POST | `/` | 상품 생성 | ProductRequestDto | void |
| GET | `/` | 상품 목록 조회 | page, size (query params) | Page<ProductResponseDto> |
| PATCH | `/{id}` | 상품 정보 수정 | ProductRequestDto | void |
| DELETE | `/{id}` | 상품 삭제 | - | void |

### 상품 옵션 API (`/api/v1/products/{productId}/options`)

| Method | Endpoint | Description | Request | Response |
|--------|----------|-------------|----------|-----------|
| POST | `/` | 상품 옵션 생성 | List<ProductOptionRequestDto> | void |
| GET | `/` | 상품 옵션 조회 | - | List<ProductOptionResponseDto> |
| PATCH | `/` | 상품 옵션 수정 | List<ProductOptionRequestDto> | void |
| DELETE | `/{optionId}` | 상품 옵션 삭제 | - | void |

## 코드 구조

```
com.frankit.product.domain
├── controller
│   ├── ProductController.java
│   └── ProductOptionController.java
├── dto
│   ├── request
│   │   ├── ProductRequestDto.java
│   │   └── ProductOptionRequestDto.java
│   └── response
│       ├── ProductResponseDto.java
│       └── ProductOptionResponseDto.java
├── service
│   ├── ProductService.java
│   └── ProductOptionService.java
└── entity
    └── OptionType.java
```

## 서비스 흐름

### 상품 관리 흐름

1. **상품 생성**
    - 클라이언트가 상품명, 설명, 가격, 배송비 정보를 전송
    - `ProductController`가 요청을 받아 `ProductService`로 전달
    - 유효성 검증 후 상품 정보 저장

2. **상품 조회**
    - 페이지네이션을 지원하는 상품 목록 조회
    - 기본값: page=0, size=10
    - `ProductPageResponse` 형태로 응답

3. **상품 수정**
    - 상품 ID를 통해 특정 상품 정보 수정
    - 입력값 유효성 검증 수행

4. **상품 삭제**
    - 상품 ID를 통해 상품 정보 삭제

### 상품 옵션 관리 흐름

1. **옵션 생성**
    - 특정 상품에 대한 옵션 정보 생성
    - 옵션 타입, 이름, 값 목록, 추가 가격 정보 포함
    - 최대 3개까지의 옵션값 설정 가능

2. **옵션 조회**
    - 특정 상품의 모든 옵션 정보 조회

3. **옵션 수정**
    - 기존 옵션 정보 수정
    - 옵션값 제한 및 가격 유효성 검증

4. **옵션 삭제**
    - 특정 옵션 ID를 통해 옵션 정보 삭제

## 유효성 검증

### ProductRequestDto
- 상품명: 필수값
- 가격: 필수값, 0 이상
- 배송비: 필수값, 0 이상

### ProductOptionRequestDto
- 옵션명: 필수값
- 옵션 타입: 필수값
- 옵션값 목록: 최대 3개
- 추가 가격: 필수값, 0 이상