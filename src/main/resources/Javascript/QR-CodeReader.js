function scanQRCodeFromFile(event) {
  const file = event.target.files[0];
  if (!file) return;

  const html5QrCode = new Html5Qrcode("reader");

  html5QrCode.scanFile(file, true)
    .then(decodedText => {
      document.getElementById("result").innerHTML = `QR Code Result: ${decodedText}`;
    })
    .catch(err => {
      document.getElementById("result").innerHTML = `Error scanning file: ${err}`;
    })
    .finally(() => {
      html5QrCode.clear();
    });
}

