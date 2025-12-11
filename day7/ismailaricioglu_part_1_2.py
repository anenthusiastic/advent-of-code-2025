from collections import deque                                               # FIFO yapısı için deque, beam yönetiminde kullanacağız

# --------------------------------------------------------------
# Tachyon Manifolds Listesinden Sıfırlanmış Örnek 1. Satır
# --------------------------------------------------------------
def create_zero_grid_from_first_row(grid: list[list[str]]) -> list[list[int]]:
    if not grid:
        raise ValueError("Grid boş olamaz.")

    column_count = len(grid[0])
    return [[0 for _ in range(column_count)]]

# --------------------------------------------------------------
# Tachyon Manifolds ışın simülasyonu (^ -> v)
# --------------------------------------------------------------
def simulate_beams_v(grid: list[list[str]]) -> int:
    """
    Grid üzerinde 'S' noktasından başlayan ışını simüle eder.
    Splitter (^) ile karşılaşınca hücreyi 'v' ile işaretler ve ışınlar yanlara ayrılır.
    Boş hücreler '|' ile doldurulur.
    Beam yönetimi deque ile kullanılır.

    Returns:
        split_count: toplam 'v' sayısı
    """
    rows = len(grid)                                                        # grid satır sayısı
    cols = len(grid[0]) if rows > 0 else 0                                  # grid sütun sayısı (satır varsa)

    # S konumunu bul (başlangıç ışını)
    start_row = next(r for r, row in enumerate(grid) if 'S' in row)         # S karakterinin bulunduğu satır
    start_col = grid[start_row].index('S')                                  # S karakterinin bulunduğu sütun

    beams = deque([(start_row, start_col)])                                 # aktif ışınların koordinatları (row, col)
    split_count = 0                                                         # toplam split sayısı
    one_row_currunt =  create_zero_grid_from_first_row(grid)                # toplam ışın yolu gridi - gridin tek satırlık ve aynı kolon sayısında
    one_row_currunt[0][start_col] = 1

    while beams:                                                            # beam listesi boşalana kadar çalış
        r, c = beams.popleft()                                              # sıradaki ışını al
        r_next = r + 1                                                      # ışın bir alt satıra ilerler

        transferee_num = one_row_currunt[0][c]
        if r_next >= rows:                                                  # gridin alt sınırını geçtiyse dur
            continue

        cell = grid[r_next][c]                                              # bir alt satırdaki hücreyi al

        if cell == '.':                                                     # boş hücre ise
            grid[r_next][c] = '|'                                           # ışını aşağı indir ve '|' ile işaretle
            beams.append((r_next, c))                                       # ışını beam listesine ekle
        elif cell == '^':                                                   # splitter ile karşılaşırsa
            grid[r_next][c] = 'v'                                           # splitter hücresini 'v' ile işaretle
            split_count += 1                                                # split sayısını artır
            if c - 1 >= 0:                                                  # sol yan boşsa
                beams.append((r_next, c - 1))                               # sol yan için yeni ışın ekle
                one_row_currunt[0][c - 1] += transferee_num                 # yol sayısı ekle
            if c + 1 < cols:                                                # sağ yan boşsa
                beams.append((r_next, c + 1))                               # sağ yan için yeni ışın ekle
                one_row_currunt[0][c + 1] += transferee_num                 # yol sayısı ekle
            one_row_currunt[0][c] = 0                                       # yolu iptal et
        else:
            continue                                                        # başka bir karakter varsa (örn. daha önceki '|', 'v' veya 'S') ışın durur

    return split_count, one_row_currunt                                     # toplam split sayısını & yol listesi döndür

# --------------------------------------------------------------
# Işın Yolları Toplamı
# --------------------------------------------------------------
def print_grid_sum(grid: list[list[int]]) -> None:
    total = 0
    for row in grid:
        for value in row:
            total += value
    return total

# --------------------------------------------------------------
# Grid - String'i listeye çevirme - FORMATLAMA
# --------------------------------------------------------------
def string_to_grid(data: str) -> list[list[str]]:
    """
    Çok satırlı string'i grid (liste içinde liste) haline çevirir.
    Her satır bir liste, her karakter bir hücre olur.
    """
    return [list(line) for line in data.split("\n")]                        # satırları ayır, her satırı listeye dönüştür

# --------------------------------------------------------------
# Grid üzerindeki tüm boştaki '^' splitter'ları temizleme
# --------------------------------------------------------------
def clear_splitters(grid: list[list[str]]) -> None:
    """
    Grid'deki tüm '^' karakterlerini '.' ile değiştirir.
    Grid üzerinde in-place (yerinde) değişiklik yapar.
    """
    for r in range(len(grid)):
        for c in range(len(grid[r])):
            if grid[r][c] == '^':
                grid[r][c] = '.'

# --------------------------------------------------------------
# MAİN CODE ----------------------------------------------------
# --------------------------------------------------------------
data = """.......S.......
...............
.......^.......
...............
......^.^......
...............
.....^.^.^.....
...............
....^.^...^....
...............
...^.^...^.^...
...............
..^...^.....^..
...............
.^.^.^.^.^...^.
..............."""

# --------------------------------------------------------------
# Gride çevir - FORMATLA
# --------------------------------------------------------------
grid = string_to_grid(data)

# --------------------------------------------------------------
# Simülasyonu çalıştır
# --------------------------------------------------------------
total_splits, one_row_currunt = simulate_beams_v(grid)

# --------------------------------------------------------------
# Simülasyon sonrası grid'i temizleyelim
# --------------------------------------------------------------
clear_splitters(grid)

# --------------------------------------------------------------
# Grid'i yazdır
# --------------------------------------------------------------
for row in grid:
    print("".join(row))

# 1. BÖLÜM -----------------------------------------------------
print("Toplam split sayısı:", total_splits)

# 2. BÖLÜM -----------------------------------------------------
#print("Her kolondaki toplam ışın sayısı:", one_row_currunt)
print("Toplam Işın Yolu Sayısı:",print_grid_sum(one_row_currunt))
