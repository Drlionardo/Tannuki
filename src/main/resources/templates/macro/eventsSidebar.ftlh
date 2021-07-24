<#macro editorList>
<div class="col-md-4 themed-grid-col">
    <div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-white" style="width: 380px;">
        <a href="/" class="d-flex align-items-center flex-shrink-0 p-3 link-dark text-decoration-none border-bottom">
            <svg class="bi me-2" width="30" height="24"><use xlink:href="#bootstrap"/></svg>
            <span class="fs-5 fw-semibold">Editable events</span>
        </a>
        <div class="list-group list-group-flush border-bottom scrollarea">
            <#if selectedEvent??>
            <a href="#" class="list-group-item list-group-item-action active py-3 lh-tight" aria-current="true">
                <div class="d-flex w-100 align-items-center justify-content-between">
                    <strong class="mb-1">${selectedEvent.name}</strong>
                    <small>${selectedEvent.id}</small>
                </div>
                <div class="col-10 mb-1 small">${selectedEvent.description}</div>
            </a>
            </#if>
            <#list creatorEvents as event>
                <a href="/editor/event/${event.id}/edit" class="list-group-item list-group-item-action py-3 lh-tight">
                    <div class="d-flex w-100 align-items-center justify-content-between">
                        <strong class="mb-1">${event.name}</strong>
                        <small>${event.id}</small>
                    </div>
                    <div class="col-10 mb-1 small">${event.description}</div>
                </a>
            </#list>
        </div>
    </div>
</div>
</#macro>
<#macro userList>
    <div class="col-md-4 themed-grid-col">
    <div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-white" style="width: 380px;">
        <a href="/" class="d-flex align-items-center flex-shrink-0 p-3 link-dark text-decoration-none border-bottom">
            <svg class="bi me-2" width="30" height="24"><use xlink:href="#bootstrap"/></svg>
            <span class="fs-5 fw-semibold">My events</span>
        </a>
        <div class="list-group list-group-flush border-bottom scrollarea">
            <#list registeredEvents as event>
                <a href="/event/${event.id}" class="list-group-item list-group-item-action py-3 lh-tight">
                    <div class="d-flex w-100 align-items-center justify-content-between">
                        <strong class="mb-1">${event.name}</strong>
                        <small>${event.id}</small>
                    </div>
                    <div class="col-10 mb-1 small">${event.description}</div>
                </a>
            </#list>
        </div>
    </div>
</div>
</#macro>
