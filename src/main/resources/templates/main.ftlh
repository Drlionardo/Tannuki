<#import "macro/template.ftlh" as tmpl>
<@tmpl.page>
    <div class="container mt-5">
        <div class="card-group">
            <div class="row row-cols-1 row-cols-md-3">
                <#list events as event>
                    <div class="col mb-4">
                        <div class="card">
                            <div class="card-header">
                                Featured
                            </div>
                            <img src="${event.image!"/img/defaultEventImage.png"}" class="card-img-top" alt="Event image">
                            <div class="card-body ">
                                <h5 class="card-title">${event.name}</h5>
                                <p class="card-text">${event.description}</p>
                                <p class="card-text"><a href="event/${event.id}" class="btn btn-primary p-2">Visit</a> <small class="text-muted">${event.id}</small></p>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</@tmpl.page>
