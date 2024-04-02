<div>
    <form action="/app-analytics/${appanalyticsdata.id}" method="post">
        <label for="name">App Name</label>
        <input type="text" value="${appanalyticsdata.name}" name="name" id ="name">
        <label for="version">App Version</label>
        <input type="text" value="${appanalyticsdata.version}" name="version" id ="version">
        <label for="downloads">Downloads</label>
        <input type="text" value="${appanalyticsdata.downloads}" name="downloads" id ="downloads">
        <button type="submit">Edit</button>
    </form>
</div>