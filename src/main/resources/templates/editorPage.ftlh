<#import "macro/template.ftlh" as tmpl>
<#import "macro/eventsSidebar.ftlh" as sidebar>
<@tmpl.page>
    <div class="container mt-5">
        <div class="row mb-3">
            <div class="col-md-8 themed-grid-col">
                <h3>Add event:</h3>
                <form method="post" action="/editor/add">
                    <div class="form-group">
                        <label for="formGroupExampleInput">Event name</label>
                        <input name="name" type="text" class="form-control" id="formGroupExampleInput" placeholder="Example input placeholder">
                    </div>
                    <div class="form-group">
                        <label for="exampleFormControlTextarea1">Description</label>
                        <textarea name="description" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                    </div>
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose event image</label>
                    </div>
                    <input type="hidden" name="_csrf" value=${_csrf.token}>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
            <@sidebar.editorList></@sidebar.editorList>
        </div>
    </div>
</@tmpl.page>

