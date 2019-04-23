# Petunjuk Penggunaan Program
Asumsi:
* Pengguna telah mempunyai minimal Java 1.8 di dalam sistem operasi nya.

Langkah Menjalankan Program:
1. Melakukan *clone* atau mengunduh zip dari repositori ini (jika mengunduh zip, lakukan ekstraksi dari zip yang telah diunduh)
2. Membuka folder hasil *clone* atau ekstraksi.
3. Membuka folder out
4. Menjalankan aplikasi *peer2peer-collaborative-editing.jar* dengan menekan ikon aplikasi sebanyak 2 kali secara cepat.

# Pembagian Tugas

|          Nama          |    NIM   |  Persentase | Deskripsi |
| ---------------------- | -------- | ----------- | --------- |
| Shandy                 | 13516097 | XX%         | Controller, Laporan     |
| Jessin D.              | 13516112 | XX%         | CRDT, Version Vector    |
| I Kadek Yuda B. G.     | 13516115 | XX%         | Messenger               |

# Laporan

## Deskripsi Program
### Cara Kerja Program
Program-program yang dimiliki oleh banyak pengguna melakukan inisialisasi menggunakan sebuah server untuk membangun
hubungan antar program melalui proses yang dinamakan ***signaling***. Kemudian, setelah koneksi antar program berhasil
dibangun, *signaling server* tidak lagi dibutuhkan. Program-program berkomunikasi langsung satu sama lain menggunakan WebRTC.

Program akan menampilkan sebuah tampilan antarmuka berisi *text editor* dan daftar *username* dari program-program lain yang
telah terkoneksi. Setiap masukan pengguna ke dalam *text editor* akan diterima oleh **MainUIController** dan dilanjutkan dengan 
memanggil fungsi *localInsert* dari kelas **CDRTController** yang akan memperbaharui struktur **CDRT** serta men-*trigger* 
kelas **Messenger** untuk mengirimkan masukan pengguna tersebut ke 
program-program lain.

Pemrosesan kiriman akan berbeda sesuai dengan jenis perintah dari kiriman tersebut. Jika perintah kiriman yakni melakukan 
penambahan karakter pada *text editor* maka program penerima akan menerima kiriman tersebut dan menverifikasikannya dengan 
kelas **Version Vector** yang dimiliki. Jika verifikasi berhasil, maka karakter dari kiriman tersebut akan ditampilkan ke *text editor*.
Akan tetapi, jika perintah kiriman yakni melakukan penghapusan karakter pada *text editor*, program menerima akan memasukkannya ke dalam
sebuah ***deletion buffer***. *Deletion buffer* akan diproses jika program menerima kiriman dari program lain untuk di cek jika
karakter yang bersangkutan pernah ditambahkan ke *text editor* dan jika pernah, karakter tersebut akan dihapus.


### Arsitektur Program
// Gambar Class Diagram (generate dari Intellij nanti).

// Penjelasan Class Diagram


## Aplikasi CDRT, Version Vector, dan Deletion Buffer


## Analisis Program


## Pengujian
### Kasus *Konkuren Editing*
Kasus Pengujian:
1. Pengguna 1 dan Pengguna 2 melakukan *insert* karakter di posisi *cursor* yang berbeda secara bersamaan.
2. Pengguna 1 dan Pengguna 2 melakukan *delete* karakter di posisi *cursor* yang berbeda.
3. Pengguna 1 dan Pengguna 2 melakukan *insert* karakter di posisi *cursor* yang sama.
4. Pengguna 1 dan Pengguna 2 melakukan *delete* karakter di posisi *cursor* yang sama.

Hasil Pengujian:
TBD

### Kasus Konsistensi
Kasus Pengujian:

Hasil Pengujian:
TBD

### Kasus *Deletion Buffer*
Kasus Pengujian:

Hasil Pengujian:
TBD

### Kasus *Version Vector*
Kasus Pengujian:

Hasil Pengujian:
TBD

## Screenshot
// Gambar Screenshot (Masukkan ke dalam repo ini aja gambarnya)

## Video
// Video demo (Link ke google drive)