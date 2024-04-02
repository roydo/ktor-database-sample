<div>
    <div>
        <h3>test-new</h3>
        <a href="#" class="download_btn_08">Download</a>
    </div>
    <div>
        <h3>test2</h3>
        <a href="#" class="download_btn_07">Download</a>
    </div>
</div>

<script>
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
    });
    
    const btn07 = document.querySelector('.download_btn_07');

    btn07.addEventListener('click', (e) => {
        e.preventDefault();
        fetch('/apps', {
            method: 'POST',
            body: JSON.stringify({ id: 7 }), // ここで必要なデータをサーバーに送信
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
    });
</script>