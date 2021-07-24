<#import "macro/template.ftlh" as tmpl>
<#include "macro/security.ftlh">
<@tmpl.page>
    <div class="container-fluid bg-white">
        <div class="container col-xxl-8 px-4 py-5">
            <div class="row flex-lg-row-reverse align-items-center g-5 py-5">
                <div class="col-10 col-sm-8 col-lg-6">
                    <img src="/img/main-logo.png" class="d-block mx-lg-auto img-fluid" alt="Event image" width="700" height="500" loading="lazy">
                </div>
                <div class="col-lg-6">
                    <h1 class="display-5 fw-bold lh-1 mb-3">Welcome to Registry hub!</h1>
                    <p class="lead">This is educational project! It allows people to register their own events or participate in others.</p>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-start">
                        <a href="/main"> <button type="button" class="btn btn-primary btn-lg px-4 me-md-2">Main page</button></a>
                        <a href="https://github.com/Drlionardo/registry-hub"> <button type="button" class="btn btn-primary btn-primary-github btn-lg px-4 me-md-2">Github page</button></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@tmpl.page>

