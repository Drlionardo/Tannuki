<#import "macro/template.ftlh" as tmpl>
<#include "macro/security.ftlh">
<@tmpl.page>
    <div class="container-fluid bg-white">
        <div class="container col-xxl-8 px-4 py-5">
            <div class="row flex-lg-row-reverse align-items-center g-5 py-5">
                <div class="col-10 col-sm-8 col-lg-6">
                    <img src="${event.image!"/img/defaultEventImage.png"}" class="d-block mx-lg-auto img-fluid" alt="Event image" width="700" height="500" loading="lazy">
                </div>
                <div class="col-lg-6">
                    <h1 class="display-5 fw-bold lh-1 mb-3">${event.name}</h1>
                    <p class="lead">${event.description}</p>
                    <p class="lead">Регистрация заканчивается: DATE</p>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-start">
                        <#if isRegistered>
                            <form method="post" action="/event/${event.id}/cancel">
                                <input type="hidden" name="_csrf" value=${_csrf.token}>
                                <button type="submit" class="btn btn-outline-secondary btn-lg px-4">Cancel</button>
                            </form>
                        <#else>
                            <form method="post" action="/event/${event.id}/register">
                                <input type="hidden" name="_csrf" value=${_csrf.token}>
                                <button type="submit" class="btn btn-primary btn-lg px-4 me-md-2">Register</button>
                            </form>
                        </#if>
                        <button type="button" class="btn btn-primary btn-lg px-4 me-md-2">Primary</button>
                        <button type="button" class="btn btn-outline-secondary btn-lg px-4">Default</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@tmpl.page>
