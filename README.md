# ✂️ Haircut Prank - Prank Sounds Multiplatform App

Ứng dụng âm thanh trêu đùa (Haircut Prank & Sound Effects) được xây dựng hoàn toàn bằng **Kotlin Multiplatform (KMP)** và **Compose Multiplatform (CMP)**, chia sẻ 100% logic nghiệp vụ và giao diện người dùng trên **Android**, **Desktop (Windows / macOS / Linux)** và **iOS**.

---

## 📱 Screenshots & Demo Tính Năng

- **14 Danh mục âm thanh độc đáo**: Hair Clipper (Tông đơ), Air Horn (Còi hơi), Fart (Đánh rắm), Burp (Ợ hơi), Gun, Car, Scary, Bomb, Meme, Siren, Animals, Taser, Toilet Flushing, Breaking.
- **280+ File âm thanh MP3 chất lượng cao**: Giải nén và phát trực tiếp qua audio engine riêng biệt trên từng nền tảng.
- **Trình phát âm thanh chuyên nghiệp**:
  - Phát / Tạm dừng mượt mà với hiệu ứng sóng âm tỏa tròn (**Ripple Wave Animation**).
  - Hẹn giờ phát (**Play After Timer**): 5s, 10s, 30s, 1 phút, 5 phút kèm đồng hồ đếm ngược trực quan.
  - Chế độ lặp lại vô hạn (**Looping**).
  - Bật / tắt chế độ rung (**Vibration**).
- **Yêu thích (Favorites)**: Lưu và quản lý các âm thanh yêu thích dễ dàng.
- **Giao diện Adaptive / Responsive**: Tự động co giãn phù hợp hoàn hảo trên điện thoại di động và màn hình máy tính (không bị méo hay giãn tỉ lệ khi phóng to toàn màn hình Desktop).
- **Màn hình Cài đặt (Settings)**: Đánh giá, phản hồi, chính sách bảo mật và hỗ trợ chia sẻ.

---

## 🏛️ Kiến Trúc Dự Án (Clean Architecture + MVI/MVVM)

Dự án tuân theo tiêu chuẩn **Clean Architecture** phân lớp chặt chẽ:

```
shared/src/commonMain/kotlin/org/example/project/
├── domain/                      # Lớp nghiệp vụ (Pure Kotlin - Zero dependencies)
│   ├── model/                  # Data models: Sound, SoundCategory, UserPreferences
│   ├── repository/             # Interfaces: SoundRepository, CategoryRepository,...
│   └── usecase/                # Các UseCase độc lập cho từng tính năng
│
├── data/                        # Lớp dữ liệu và Audio Engine
│   ├── player/                 # AudioPlayerManager & PlatformAudioPlayer (expect/actual)
│   ├── repository/             # Triển khai các Repository (SoundRepositoryImpl,...)
│   └── storage/                # Quét và nạp dữ liệu file âm thanh (PlatformSoundStorage)
│
└── presentation/                # Giao diện người dùng (Compose Multiplatform)
    ├── navigation/             # Type-safe Navigation (AppNavHost, Screen routes)
    ├── screens/
    │   ├── splash/             # Splash Screen khởi tạo dữ liệu
    │   ├── intro/              # Giới thiệu Onboarding với Pager
    │   ├── main/               # Scaffold chứa Custom Bottom Navigation Bar
    │   ├── home/               # Danh sách 14 Categories dạng Grid
    │   ├── favorite/           # Quản lý âm thanh yêu thích
    │   ├── listsound/          # Danh sách âm thanh theo từng Category
    │   ├── detail/             # Trình phát chi tiết âm thanh và hẹn giờ
    │   └── setting/            # Màn hình cài đặt
    └── theme/                  # Màu sắc, Typography, gradient chủ đạo
```

---

## 🔊 Cơ Chế Âm Thanh Đa Nền Tảng (Cross-Platform Audio Engine)

Dự án áp dụng mô hình `expect / actual` để giải quyết bài toán phát âm thanh MP3 trên các hệ điều hành:

```
                       ┌─────────────────────────┐
                       │   AudioPlayerManager    │ (commonMain - Quản lý StateFlow)
                       └────────────┬────────────┘
                                    │ delegates
                       ┌────────────▼────────────┐
                       │   PlatformAudioPlayer   │ (expect interface)
                       └──────┬───────────┬──────┘
             actual           │           │ actual
      ┌───────────────────────▼──┐     ┌──▼──────────────────────────┐
      │ AndroidAudioPlayer       │     │ JvmAudioPlayer              │
      │ (android.media.MediaPlayer│    │ (JLayer 1.0.1 MP3 Decoder    │
      │  + Android Vibrator)     │     │  chạy trên Dispatchers.IO)  │
      └──────────────────────────┘     └─────────────────────────────┘
```

- **Desktop (JVM)**: Tích hợp thư viện `javazoom:jlayer:1.0.1` giải mã MP3 siêu nhẹ, phát trực tiếp ra card âm thanh máy tính, hỗ trợ vòng lặp vô tận và dừng tức thời.
- **Android**: Sử dụng `android.media.MediaPlayer` tích hợp phần cứng của Android.
- **iOS**: Sẵn sàng mở rộng qua `AVAudioPlayer` (`AVFoundation`).

---

## 🖥️ Tối Ưu Hóa Giao Diện Desktop (Adaptive Layout)

1. **Window State & Minimum Constraints**:
   - Cửa sổ khởi tạo ở kích thước tối ưu `960x750dp`.
   - Giới hạn kích thước tối thiểu `window.minimumSize = Dimension(380, 600)` để ngăn giao diện bị tràn.
2. **Container Max-Width Centering**:
   - Mọi màn hình được bọc trong container `widthIn(max = 680.dp...1200.dp)` và căn giữa `Alignment.TopCenter`.
   - Khi người dùng phóng to toàn màn hình Desktop, nền gradient bao phủ trọn vẹn trong khi nội dung điều khiển giữ nguyên tỉ lệ vàng, không bị méo lệch hay kéo dãn.
3. **Adaptive Bottom Navigation Dock**:
   - Thanh điều hướng tự động co giãn (`widthIn(max = 500.dp)`) dạng Floating Dock nổi hiện đại.

---

## 🛠️ Công Nghệ Sử Dụng (Tech Stack)

| Thành phần | Công nghệ / Thư viện |
|---|---|
| **Ngôn ngữ** | Kotlin 2.0+ (Multiplatform) |
| **Giao diện** | Compose Multiplatform 1.7+ |
| **Điều hướng** | Jetpack Navigation Compose (Type-safe routes với `@Serializable`) |
| **Quản lý State** | Lifecycle ViewModel (`lifecycle-viewmodel-compose`) & Coroutines Flow |
| **Hoạt họa / Lottie** | Compottie (Lottie animation player for CMP) |
| **Audio Engine** | JLayer 1.0.1 (Desktop JVM) + Android MediaPlayer |
| **Build Tool** | Gradle 8.11+ (Version Catalogs `libs.versions.toml`) |

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Ứng Dụng

### Yêu Cầu Môi Trường
- **JDK**: Java Development Kit 17 hoặc 21.
- **IDE**: Android Studio Ladybug / IntelliJ IDEA 2024.2+.
- (Tùy chọn cho iOS): macOS + Xcode 15+.

### Lệnh Chạy Ứng Dụng

#### 1. Chạy trên Desktop (Windows / macOS / Linux)
```bash
# Chạy ứng dụng trực tiếp
./gradlew :desktopApp:run

# Hoặc chạy chế độ Hot Reload
./gradlew :desktopApp:hotRun --auto
```

#### 2. Chạy trên Android
```bash
# Build file APK Debug
./gradlew :androidApp:assembleDebug

# Cài đặt và chạy trên thiết bị / máy ảo kết nối
./gradlew :androidApp:installDebug
```

#### 3. Chạy trên iOS
Mở thư mục `iosApp/` bằng **Xcode** và bấm `Run` trên Simulator hoặc thiết bị thật.
chưa test ios (vì không có máy =)) )
---

## 📄 License
Phát triển cho mục đích giải trí và học tập.
Bản quyền thuộc về dự án Haircut Prank.