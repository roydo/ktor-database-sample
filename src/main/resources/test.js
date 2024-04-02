const btn08 = document.querySelector('.download_btn_08');

btn08.addEventListener('click', (e) => {
    e.preventDefault();
    fetch('/apps', {
        method: 'POST',
        body: JSON.stringify({ id: 8 }), // ここで必要なデータをサーバーに送信
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        // レスポンスが成功したら、実際のダウンロードを開始
        if (response.ok) {
            window.location.href = "/app-analytics";
        }
    }).catch(error => {
        console.error('Error:', error);
    });
})