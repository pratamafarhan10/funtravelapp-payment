# Payment Service
Service ini bertujuan untuk mengelola pembayaran customer dan mengelola rekening customer.

## Pembayaran
Modul pembayaran memiliki fitur sebagai berikut:
- Membuat transaksi  (menerima message dari order service melalui kafka)
- Update status pembayaran (mengirim message ke order service melalui kafka)
- Mengirim notification ke notification service melalui kafka
- Get all pembayaran
- Get pembayaran by id

## Rekening
Modul rekening memiliki fitur sebagai berikut:
- Membuat rekening
- Mengubah data rekening
- Melihat data rekening
- Menghapus data rekening
