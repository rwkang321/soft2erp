

## soft2erp ERP 시스템

### 개발 관련
```
Backend
- JDK21
- Spring Boot, Spring MVC
- Thymeleaf
Frontend
- Nexacro, Bun
- JavaScript
Database
- Supabase
- PostgreSQL
```

### Spring Boot
```
1. 설정 항목
web.xml
applicationContext.xml
dispatcher-servlet.xml
DataSource 설정
Transaction 설정
Tomcat 설정
라이브러리 버전 맞추기
2. 설정 자동화
@SpringBootApplication
application.yml
내장 Tomcat
Auto Configuration
Starter Dependency
===>
Spring Framework = 기반 기술
Spring MVC       = 웹 요청 처리 기술
Spring Boot      = Spring을 쉽게 실행/배포하게 해주는 틀
```

### Spring Boot: Controller/Service/Repository
```
1. Controller
품목조회 버튼 클릭
  ↓
Nexacro transaction()
  ↓
/item/search 호출
  ↓
ItemController.search()
===> Controller 가 하는 일
요청 URL 받기
요청 파라미터 받기
요청 데이터 검증 일부
Service 호출
결과를 Nexacro/JSON으로 반환
===> Controller 가 해서는 안 되는 일.
복잡한 업무 로직
SQL 작성
DB 직접 처리
많은 if/else 업무 규칙
트랜잭션 세부 처리
예)
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> search(ItemSearchCondition condition) {
        return itemService.searchItems(condition);
    }

    @PostMapping
    public SaveResult save(@RequestBody List<ItemDto> items) {
        return itemService.saveItems(items);
    }
}

2. Service
품목코드 중복 확인
필수값 확인
사용중인 품목 삭제 방지
품목구분 코드 유효성 확인
저장 이력 생성
여러 테이블 동시 저장
트랜잭션 처리
예)
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemDto> searchItems(ItemSearchCondition condition) {
        return itemRepository.findItems(condition);
    }

    public SaveResult saveItems(List<ItemDto> items) {
        for (ItemDto item : items) {
            validateItem(item);

            if (item.isNew()) {
                itemRepository.insertItem(item);
            } else if (item.isModified()) {
                itemRepository.updateItem(item);
            } else if (item.isDeleted()) {
                itemRepository.deleteItem(item.getItemCode());
            }
        }

        return SaveResult.success();
    }

    private void validateItem(ItemDto item) {
        if (item.getItemCode() == null || item.getItemCode().isBlank()) {
            throw new BusinessException("품목코드는 필수입니다.");
        }
    }
}

3. Repository
SELECT
INSERT
UPDATE
DELETE
Stored Procedure 호출
Mapper 호출
JPA Entity 저장
예) 일반
@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ItemDto> findItems(ItemSearchCondition condition) {
        String sql = """
            SELECT item_code, item_name, item_type, use_yn
            FROM item_master
            WHERE item_name LIKE ?
            ORDER BY item_code
        """;

        return jdbcTemplate.query(
            sql,
            new Object[]{"%" + condition.getItemName() + "%"},
            (rs, rowNum) -> new ItemDto(
                rs.getString("item_code"),
                rs.getString("item_name"),
                rs.getString("item_type"),
                rs.getString("use_yn")
            )
        );
    }
}
예) MyBatis
@Mapper
public interface ItemMapper {
    List<ItemDto> findItems(ItemSearchCondition condition);
    int insertItem(ItemDto item);
    int updateItem(ItemDto item);
    int deleteItem(String itemCode);
}


4. DB SQL
SELECT item_code,
       item_name,
       item_type,
       unit,
       use_yn
FROM tb_item
WHERE item_name LIKE '%' || #{itemName} || '%'
ORDER BY item_code

5. DTO
public class ItemDto {
    private String itemCode;
    private String itemName;
    private String itemType;
    private String unit;
    private String useYn;
}
===> Nexacro Dataset의 row 한 줄이 Java에서는 DTO 하나가 된다
Nexacro Dataset row
  ≒
Java DTO object

6. SearchCondition
조회조건도 반드시 별도 객체로 만든다
public class ItemSearchCondition {
    private String itemCode;
    private String itemName;
    private String itemType;
    private String useYn;
}

7. Entity for JPA: 가능하면 MyBatis 방식으로 개발하고 꼭 필요한 경우만 JPA
@Entity
@Table(name = "tb_item")
public class Item {
    @Id
    private String itemCode;

    private String itemName;
    private String itemType;
    private String unit;
    private String useYn;
}

8. JPA vs MyBatis
중요: 가능한 MyBatis 방식으로 개발 하고, 꼭 필요한 경우에만 JPA 방식 개발.
JPA: 객체 중심
Java Entity 중심
SQL을 직접 적게 작성
객체 저장 → DB 반영
장점:
단순 CRUD 생산성 좋음
객체 모델 관리 좋음
표준 기술
Spring Data JPA와 궁합 좋음
단점:
복잡한 ERP SQL에서는 학습 부담
성능 튜닝 이해 필요
조인/통계/동적 쿼리에서 답답할 수 있음
기존 SQL 경험을 바로 활용하기 어려움
MyBatis: SQL 중심
SQL 직접 작성
Mapper XML 또는 Annotation 사용
결과를 DTO에 매핑
장점:
SQL 직접 작성
Mapper XML 또는 Annotation 사용
결과를 DTO에 매핑
단점:
ERP 개발자에게 익숙함
복잡한 SQL 작성 편함
Oracle/MSSQL/MySQL 경험 활용 가능
동적 쿼리 작성 용이
성능 튜닝 명확   
```

---
---

### System Scaffold
```
공통코드
공통응답
공통예외
공통로그
보안관리
권한관리

Lombok 사용 여부
Log Level
Test Code
Swagger / OpenAPI
배포 구조
Transaction: @Transactional 사용
Exception
Validation
Maven / Gradle
Profile: dev/test/prod 분리
REST API vs Nexacro Transaction
Directory Schema

기준정보
영업관리
구매관리
자재관리
생산관리
품질관리
재고관리
출하관리
원가관리
시스템관리
```

### 공통 코드
```
품목구분
거래처구분
단위
사용여부
공정코드
창고코드
불량유형

Spring
CommonCodeController
CommonCodeService
CommonCodeRepository

Nexacro
ds_common_code
Combo 바인딩
공통 코드 조회 함수
```
### 공통 응답: API 응답 => 패턴 통일
```
Java
public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;
}

JSON
{
  "success": true,
  "code": "OK",
  "message": "조회되었습니다.",
  "data": [...]
}
```

### 공통 예외
```
BusinessException
SystemException
ValidationException
UnauthorizedException
```

### 공통 로그
```
누가
언제
어떤 화면에서
무슨 데이터를
등록/수정/삭제했는가

Java/Spring
접속 로그
사용자 작업 로그
저장 이력
오류 로그
API 호출 로그
```

### 보안, 로그인, 권한
1. Session 기반 로그인
2. JWT 기반 로그인
3. SSO 연동
4. 고객사 AD/LDAP 연동
```
로그인 성공
  ↓
사용자 ID 확인
  ↓
부서/역할 확인
  ↓
메뉴 권한 조회
  ↓
버튼 권한 조회
  ↓
데이터 권한 적용
```

### Lombok 사용 여부
```
@Getter
@Setter
public class ItemDto {
    private String itemCode;
    private String itemName;
}
<=== getter/setter를 직접 써야
public String getItemCode() {
    return itemCode;
}

public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
}

```

### 로그 라벨
ERP에서는 특히 저장 오류, DB 오류, 외부 연동 오류 로그가 중요
```
TRACE
DEBUG
INFO
WARN
ERROR
```

### 테스트: JUnit5, Service 단위 테스트 필수
핵심 Service 단위 테스트
주요 API 테스트
저장 로직 테스트
마감/권한/중복 체크 테스트
```
@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    void 품목조회테스트() {
        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setItemName("BUMPER");

        List<ItemDto> result = itemService.searchItems(condition);

        assertThat(result).isNotEmpty();
    }
}
```

### Swagger / OpenAPI: API 목록 문서화
Nexacro와 Spring Boot 간 인터페이스는 API 명세 또는 Swagger/OpenAPI 형태로 정리해두면 화면 개발과 서버 개발을 분리
```
/api/items/search
/api/items/save
/api/customers/search
/api/codes
```

### 배포 구조
```
java -jar erp-server.jar
또는 Linux.systemd 서비스 등록
Nginx
  ↓
Spring Boot Embedded Tomcat
  ↓
DB

운영 구조
1. Spring Boot 단독 실행
2. Nginx + Spring Boot
3. Apache + Tomcat
4. Docker + Spring Boot
5. Kubernetes + Spring Boot

Simple 구조
Nginx + Spring Boot jar + DB  

```




## Java 개발 필수 지식
### 기본 문법
```
class
interface
extends / implements
public / private
static
final
List / Map
Exception
Generic
Stream
LocalDate / LocalDateTime

특히, List, Map, LocalDateTime, BigDecimal 
```

### BigDecimal
```
BigDecimal amount = price.multiply(quantity);
금액, 단가, 수량 계산에는 double 쓰면 안 됨
단가
수량
금액
환율
원가
세액
```

### 날짜 처리: LocalDate, LocalDateTime 사용 필수, 예전 Date 
```
LocalDate orderDate;
LocalDateTime createdAt;
일자: LocalDate
일시: LocalDateTime
시간대 포함: ZonedDateTime
```

### Collection: List, Map
```
List<ItemDto> items;
Map<String, String> codeMap;
```

### Interface
처음에는 꼭 Interface를 모든 Service에 만들 필요는 없습니다.  
하지만 큰 프로젝트에서는 인터페이스 기반 설계를 쓰기도
```
public interface ItemService {
    List<ItemDto> searchItems(ItemSearchCondition condition);
}
```

### Dependency Injection
```
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
}
ItemService가 ItemRepository를 필요로 한다
Spring이 자동으로 ItemRepository 객체를 넣어준다
```

### Annotation: 상용
```
@SpringBootApplication
@RestController
@Controller
@Service
@Repository
@Component
@RequestMapping
@GetMapping
@PostMapping
@RequestBody
@RequestParam
@PathVariable
@Transactional
```

### Transaction 처리
```
CONNECT USING SQLCA;

dw_master.Update()
dw_detail.Update()

IF SQLCA.SQLCode = 0 THEN
    COMMIT USING SQLCA;
ELSE
    ROLLBACK USING SQLCA;
END IF
===>
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    @Transactional
    public void saveOrder(OrderSaveDto dto) {
        orderMapper.updateOrderHeader(dto.getHeader());
        orderMapper.deleteOrderDetails(dto.getOrderNo());

        for (OrderDetailDto detail : dto.getDetails()) {
            orderMapper.insertOrderDetail(detail);
        }
    }
}
===>
수주 헤더 저장 성공
수주 상세 1 저장 성공
수주 상세 2 저장 실패
  ↓
전체 Rollback
===>
Controller에 붙이는 것보다
Service 업무 단위에 붙이는 것이 좋음
```

### 예외 처리
###### 공통 예외 처리
```
업무 오류
DB 오류
권한 오류
세션 만료
중복 오류
필수값 오류
마감 오류
```
Ex)
```
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleBusinessException(BusinessException e) {
        return new ErrorResponse("BUSINESS_ERROR", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse("SYSTEM_ERROR", "시스템 오류가 발생했습니다.");
    }
}
===>
Service에서 BusinessException 발생
  ↓
GlobalExceptionHandler에서 잡음
  ↓
정해진 오류 응답 형식으로 Nexacro에 반환
  ↓
Nexacro에서 메시지 표시
```

### Validation
```
품목코드 필수
거래처코드 필수
수량은 0보다 커야 함
단가는 음수 불가
마감월이면 저장 불가
사용중인 품목 삭제 불가
===> 2point validation
1. Nexacro 화면 검증
필수값 빠짐
날짜 형식 오류
수량 미입력
2. Spring Service 검증
private void validateItem(ItemDto item) {
    if (item.getItemCode() == null || item.getItemCode().isBlank()) {
        throw new BusinessException("품목코드는 필수입니다.");
    }

    if (item.getItemName() == null || item.getItemName().isBlank()) {
        throw new BusinessException("품목명은 필수입니다.");
    }
}
===>
필수값과 단순 형식 검증은 Nexacro 화면에서도 처리하되, 업무 규칙과 DB 정합성 검증은 Spring Service 계층에서 최종 처리하는 구조가 안전
```

### Maven / Gradle
```
Maven  = pom.xml
Gradle = build.gradle
```

### application.yml
```
server:
  port: 8080

spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=erp
    username: erp_user
    password: erp_password
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver

mybatis:
  mapper-locations: classpath:/mapper/**/*.xml
  type-aliases-package: com.company.erp
```

### Profile: 개발/테스트/운용 환경 분리
###### 개발/테스트/운영 DB 접속 정보는 Spring Profile로 분리하고, 운영 비밀번호나 민감 정보는 별도 환경변수 또는 서버 설정으로 관리
```
application-dev.yml
application-test.yml
application-prod.yml
===>
# application-dev.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/erp_dev
===>
# application-prod.yml
spring:
  datasource:
    url: jdbc:mysql://prod-db:3306/erp_prod
===> 실행할 때
java -jar erp.jar --spring.profiles.active=prod
```

### REST API vs Nexacor Transaction
```
1. REST API
GET    /api/items
POST   /api/items
PUT    /api/items/{id}
DELETE /api/items/{id}
2. Nexacro Transaction
REST JSON 방식
{
  "itemCode": "A001",
  "itemName": "FR BUMPER",
  "useYn": "Y"
}
Nexacro Dataset/XML 방식
Nexacro Grid/Dataset과 궁합 좋음
변경 row 처리에 유리
기존 Nexacro SI 방식에서 많이 사용
```

### Directory Schema
```
com.soft2erp.erp
 ├─ common
 │   ├─ config
 │   ├─ exception
 │   ├─ response
 │   ├─ security
 │   └─ util
 │
 ├─ system
 │   ├─ menu
 │   ├─ user
 │   ├─ role
 │   └─ code
 │
 ├─ master
 │   ├─ item
 │   ├─ customer
 │   ├─ vendor
 │   └─ employee
 │
 ├─ sales
 │   ├─ order
 │   ├─ shipment
 │   └─ invoice
 │
 ├─ production
 │   ├─ plan
 │   ├─ workorder
 │   └─ result
 │
 ├─ inventory
 │   ├─ stock
 │   ├─ receipt
 │   └─ issue
 │
 └─ purchase
     ├─ po
     ├─ receipt
     └─ payment
```


---
---

## 시스템 Scaffold Description

### 기준 정보
```
품목 마스터: master.item
거래처 마스터: master.customer
고객사 마스터
공정 마스터
설비 마스터
창고 마스터
공통코드: system.code, system.user
BOM
라우팅
```

### 영업 관리: sales.order
```
수주 등록: sales.order
수주 변경
납기 관리
출하 요청
매출 관리
```

### 구매 / 자재:purchase.po
```
발주:purchase.po
입고
검수
자재 출고
자재 재고
```

### 생산 관리: production.workorder
```
생산계획
작업지시: production.workorder
생산실적
공정실적
불량 등록
```

### 품질 관리: quality.inspection
```
수입검사
공정검사
출하검사
불량 유형
검사 성적서
```

### 재고 관리: inventory.stock
```
현재고
수불부
창고 이동
재고 조정
LOT 재고
```