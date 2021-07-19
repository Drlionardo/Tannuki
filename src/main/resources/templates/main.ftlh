<#import "macro/template.ftlh" as tmpl>
<@tmpl.page>
    <div class="container">
            <div class="col-xl mt-5">
                <h2>Hello, world</h2>
                <h3>Add event:</h3>
                <form method="post" action="/main">
                    Name<input type="text" name="name">
                    Description<input type="text" name="description">
                    <input type="hidden" name="_csrf" value=${_csrf.token}>
                    <button type="submit">Create event</button>
                </form>
                <#list events as event>
                    <div>Id:${event.id} <a href="event/${event.id}">Link</a> </div>
                    <div>Name:${event.name}</div>
                    <div>
                        Admins:
                        <#list event.admins as admin>admin</#list>
                    </div>
                    <div>Description:${event.description}</div>
                </#list>

                <div class="card bg-light mb-3" style="max-width: 18rem;">
                    <div class="card-header">Header</div>
                    <div class="card-body">
                        <h5 class="card-title">Light card title</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
<div class="main-content">

</div>
</@tmpl.page>
