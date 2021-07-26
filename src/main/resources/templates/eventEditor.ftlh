<#import "macro/template.ftlh" as tmpl>
<#import "macro/eventsSidebar.ftlh" as sidebar>
<@tmpl.page>
    <div class="container mt-5">
        <div class="row mb-3">
            <div class="col-md-8 themed-grid-col">
                <div>Id:${selectedEvent.id} </div>
                <div><h2>Event editor</h2></div>
                <form method="post" action="/editor/event/${selectedEvent.id}/edit">
                    <input type="text" name="eventName" value="${selectedEvent.name}">
                    <input type="text" name="eventDescription" value="${selectedEvent.description}">
                    <input type="hidden" name="_csrf" value=${_csrf.token}>
                    <button type="submit">Update event</button>
                </form>
                <div>
                    Admins:
                    <#list selectedEvent.admins as admin>
                        <div>${admin.username}(${admin.email})
                            <form method="post" action="/editor/event/${selectedEvent.id}/deleteAdmin">
                                <button type="submit">Delete</button>
                                <input type="hidden" name="adminId" value="${admin.id}">
                                <input type="hidden" name="_csrf" value=${_csrf.token}>
                            </form>
                        </div>
                    </#list>
                    Add admin
                    <form method="post" action="/editor/event/${selectedEvent.id}/addAdmin">
                        Admin username:
                        <input type="text" name="adminEmail" placeholder="Email">
                        <input type="hidden" name="_csrf" value=${_csrf.token}>
                        <button type="submit">Add new admin</button>
                    </form>
                </div>
            </div>
            <@sidebar.editorList></@sidebar.editorList>
        </div>
        <div class="container mt-5">
            <h3>Add Post:</h3>
            <form method="post" action="/editor/event/${selectedEvent.id}/post">
                <div class="form-group">
                    <label for="formGroupExampleInput">Post name</label>
                    <input name="title" type="text" class="form-control" id="formGroupExampleInput">
                </div>
                <div class="form-group">
                    <label for="exampleFormControlTextarea1">Description</label>
                    <textarea name="text" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                </div>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose post image</label>
                </div>
                <input type="hidden" name="_csrf" value=${_csrf.token}>
                <button type="submit" class="btn btn-primary">Add post</button>
            </form>
        </div>
    </div>

    <div class="container mt-5">
        <h2>Event info:</h2>
        <div>Id:${selectedEvent.id}</div>
        <div>Name:${selectedEvent.name}</div>
        <div>
            Admins:
            <#list selectedEvent.admins as admin>
                ${admin.username}
            </#list>
        </div>
        <div>Description:${selectedEvent.description}</div>

        <div> All users:
            <#list requestList as req>
                <div><a href="/profile/${req.owner.id}">${req.owner.username} </a> ${req.registrationTime}</div>
            </#list>
        </div>
    </div>
</@tmpl.page>



