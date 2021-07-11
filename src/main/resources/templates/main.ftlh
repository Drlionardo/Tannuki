<#import "macro/template.ftlh" as tmpl>
<@tmpl.page>
    <h2>Hello, world</h2>
    <form method="post" action="/logout">
        <input type="hidden" name="_csrf" value=${_csrf.token}>
        <button class="btn btn-primary ml-2" type="submit">Log out</button>
    </form>
    <h3>Add event:</h3>
    <form method="post" action="/main">
        Name<input type="text" name="name">
        Description<input type="text" name="description">
        <input type="hidden" name="_csrf" value=${_csrf.token}>
        <button type="submit">Create event</button>
    </form>


    <#list events as event>
        <div>Id:${event.id}</div>
        <div>Name:${event.name}</div>
        <div>
            Admins:
            <#list event.admins as admin>admin</#list>
        </div>
        <div>Description:${event.description}</div>
    </#list>
</@tmpl.page>
