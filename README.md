## 🚀 Enhancement: ByteSize & TimeDuration Parsers for Wrangler

This project enhances the CDAP Wrangler core to support human-readable **byte size** and **time duration** units directly in directives and transformations.

---

### 📖 Background

Previously, Wrangler required manual parsing and transformation of strings like `"1MB"` or `"2min"`, making recipes verbose and error-prone. This update introduces:

- Native parsing of `ByteSize` (e.g., `1KB`, `2.5MB`, `1GB`)
- Native parsing of `TimeDuration` (e.g., `150ms`, `2s`, `1h`)
- An aggregate directive that leverages these types for easier recipe writing

---

## 🧩 New Token Types

### ✅ ByteSize

| Unit | Example Input | Interpreted As |
|------|---------------|----------------|
| B    | `100B`        | 100 bytes      |
| KB   | `1KB`         | 1024 bytes     |
| MB   | `1.5MB`       | 1,572,864 bytes|
| GB   | `1GB`         | 1,073,741,824 bytes |
| TB   | `1TB`         | 1,099,511,627,776 bytes |

### ✅ TimeDuration

| Unit  | Example Input | Interpreted As     |
|-------|---------------|--------------------|
| ms    | `150ms`       | 150 milliseconds   |
| s     | `2s`          | 2000 milliseconds  |
| min   | `1min`        | 60000 milliseconds |
| h/hr  | `1h` / `1hr`  | 3600000 milliseconds |

---

## 🆕 Directive: `aggregate-stats`

This directive allows row-level aggregation of byte sizes and time durations.

### 📦 Usage

aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec
📥 Arguments
Argument	Type	Description
:sourceSize	ColumnName	Input column containing byte size strings
:sourceTime	ColumnName	Input column with time duration strings
total_size_mb	Text	Output column for total size in MB
total_time_sec	Text	Output column for total time in seconds
## 🛠️ Implementation Details

### 📜 Grammar Update
- `Directives.g4` was modified to include two new tokens:
  - `BYTE_SIZE`
  - `TIME_DURATION`

These enable parsing of human-readable units like `1.5MB` or `2min` directly in Wrangler recipes.

---

### 📁 New Java Classes

- **`ByteSize.java`**
  - Parses strings like `"10KB"`, `"1.5MB"`, `"2GB"`
  - Converts them to raw bytes using `getBytes()` method

- **`TimeDuration.java`**
  - Parses strings like `"150ms"`, `"2s"`, `"1min"`, `"1h"`
  - Converts them to milliseconds using `getMilliseconds()` method

---

### 🧩 Directive

- **`AggregateStats.java`**
  - A multi-row aggregation directive
  - Accepts two input columns (byte size and time duration)
  - Outputs total (or average) size in MB and time in seconds
  - Uses new parsers for size and time internally

---

### 🏷️ Annotations

- **`@Aggregate`**
  - Declares the directive as one that performs aggregation across multiple rows

- **`@DirectiveName`**
  - Registers the directive with a name, usage pattern, and description

  ```java
  @DirectiveName(
      name = "aggregate-stats",
      usage = "aggregate-stats <sourceByteSize> <sourceTimeDuration> <targetByteCol> <targetTimeCol>",
      description = "Aggregates byte and time values from multiple rows"
  )
ng these new types

Annotation:

@Aggregate, @DirectiveName – Used for directive registration and metadata
## ✅ Sample Input and Output

### 📥 Input Rows

| data_transfer_size | response_time |
|--------------------|----------------|
| 1MB                | 500ms          |
| 2.5MB              | 1.5s           |
| 1GB                | 2min           |

---

### 📤 Output (Aggregated Row)

| total_size_mb | total_time_sec |
|---------------|----------------|
| 1027.50       | 122.00         |

---

### 🔍 Calculation Breakdown

- **Byte Size Total**:
  - `1MB` = 1 × 1024 = 1024 MB
  - `2.5MB` = 2.5 MB
  - `1GB` = 1024 MB
  - **Total = 1024 + 2.5 + 1024 = 2050.5 MB → converted to 1027.50 MB after byte conversion**

- **Time Duration Total**:
  - `500ms` = 0.5s
  - `1.5s` = 1.5s
  - `2min` = 120s
  - **Total = 0.5 + 1.5 + 120 = 122.0s**
### 🔗 Repository Structure
wrangler-api/
├── parser/
│   ├── ByteSize.java
│   ├── TimeDuration.java
│   ├── ColumnName.java
│   ├── Text.java

wrangler-core/
├── directive/
│   ├── AggregateStats.java
│
├── parser/
│   ├── ByteTimeDirectiveTest.java
│
├── grammar/
│   └── Directives.g4 (updated with BYTE_SIZE and TIME_DURATION)

## 🧪 Testing Strategy

Unit tests are added in `ByteTimeDirectiveTest.java` to ensure correctness and robustness.

### ✅ Covered Scenarios:

- **Valid Values**
  - `1KB`, `1.5MB`, `1GB`, `2s`, `1min`
- **Edge Cases**
  - `0KB`, mixed case (`1kb`), values with extra spaces (`" 1 MB "`)
- **Invalid Values**
  - Strings like `"5hours"`, `"12megabytes"` throw exceptions
- **Directive Integration**
  - Validates aggregation behavior with multiple rows and mixed units

### ▶️ Run Tests

mvn test
## 🤖 AI Tools Usage

We used **ChatGPT** to assist with the development and documentation of this assignment.

### Areas Where AI Helped:

- ✅ Designing and structuring unit test cases
- ✅ Creating utility classes such as:
  - `ByteSize`
  - `TimeDuration`
- ✅ Writing boilerplate and structure for:
  - `AggregateStats` directive
- ✅ Clarifying usage and implementation of annotations:
  - `@Aggregate`
  - `@DirectiveName`
- ✅ Enhancing the structure, formatting, and clarity of this `README.md`
## 🙌 Contribution & Contact

This enhancement was implemented by:

**Vikash Gupta**  
GitHub: https://github.com/vik802207
Email: Vikashg802207@gmail.com

Forked from: (https://github.com/data-integrations/wrangler)

