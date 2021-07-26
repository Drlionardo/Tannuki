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
                    <#if event.registrationEndDate??>
                        <p class="lead">Регистрация заканчивается: ${event.registrationEndDate}</p>
                    </#if>
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
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container mt-5" style="max-width: 1080px">
        <#list postList as post>
            <div class="card mb-3 flex-fill">
                <#if post.imagePath??>
                    <!--  If has image --> <img src="${post.imagePath!"/img/defaultEventImage.png"}" class="card-img-top align-content-center rounded mx-auto d-block" alt="..." style="max-height: 300px; width: auto">
                </#if>
                <div class="card-body">
                    <h5 class="card-title">${post.title}</h5>
                    <p class="card-text">${post.text}</p>
                    <p class="card-text"><small class="text-muted">Published by ${post.author.username} at ${post.publicationDate}</small></p>
                </div>

                <div class="accordion accordion-flush" id="accordionFlush${post.id}">
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="flush-headingOne">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapse${post.id}" aria-expanded="false" aria-controls="flush-collapseOne">
                                ${post.commentsCounter()} comments
                            </button>
                        </h2>
                        <div id="flush-collapse${post.id}" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlush${post.id}">
                            <div class="accordion-body">
                                <div class="card mb-3">
                                    <#list post.comments as comment>
                                        <div class="row g-0">
                                            <div class="col-md-1">
                                                <img src="/img/main-logo.png" class="img-fluid rounded-start" alt="...">
                                            </div>
                                            <div class="col-md-8">
                                                <div class="card-body">
                                                    <h5 class="card-title">${comment.author.username}</h5>
                                                    <p class="card-text">${comment.text}</p>
                                                    <p class="card-text"><small class="text-muted">Published by ${comment.author.username} at ${comment.publicationDate}</small></p>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                                <p>
                                    <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#addCommentForm${post.id}" aria-expanded="false" aria-controls="addCommentForm">
                                        Add comment
                                    </button>
                                </p>
                                <div class="collapse" id="addCommentForm${post.id}">
                                    <div class="card card-body">
                                        <form method="post" action="/event/${event.id}/post/${post.id}/addComment">
                                            <div class="form-group">
                                                <label for="exampleFormControlTextarea1">Comment:</label>
                                                <textarea name="commentText" class="form-control" id="exampleFormControlTextarea1" rows="2"></textarea>
                                            </div>
                                            <input type="hidden" name="_csrf" value=${_csrf.token}>
                                            <button type="submit" class="btn btn-primary">Send</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@tmpl.page>
