
<div>
    <table>
        <tr>
            <th>id</th>
            <th>App</th>
            <th>Version</th>
            <th>Downloads</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <#list appanalyticsdata?reverse as appanalytics>
        <tr>
            <td>${appanalytics.id}</td>
            <td>${appanalytics.name}</td>
            <td>${appanalytics.version}</td>
            <td>${appanalytics.downloads}</td>
            <td><a href="app-analytics/${appanalytics.id}">Edit</td>
            <td><form action="/app-analytics" method="post">
                    <button type="submit" name="delete" value="${appanalytics.id}">Delete</button>
                </form>
            </td>
        </tr>
        </#list>
    </table>
    
    <form action="/app-analytics" method="post">
        <label for="name">App Name</label>
        <input type="text" name="name" id="name">
        <label for="name">Version</label>
        <input type="text" name="version" id="version">
        <button type="submit" name="add">Add</button>
    </form>
</div>
