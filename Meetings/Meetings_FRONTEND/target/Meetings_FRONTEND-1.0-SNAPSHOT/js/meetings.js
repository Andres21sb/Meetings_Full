/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

class Meetings {
    dom;
    modal; // login modal

    state;  // state variables: if any

    constructor() {
        this.state = {
            meetings: []
        };
        this.dom = this.render();
        this.modal = new bootstrap.Modal(this.dom.querySelector('#meetings>#modal'));
        this.dom.querySelector("#New").addEventListener('click', e => this.newMeeting());
    }

    render = () => {
        const html = `
            ${this.renderBody()} 
            ${this.renderModal()}
        `;
        var rootContent = document.createElement('div');
        rootContent.id = 'meetings';
        rootContent.innerHTML = html;
        return rootContent;
    }

    renderBody = () => {
        var html = `
        <div id="list" class="container">     
            <div class="card bg-light">
                <div class="card-body mx-auto w-75" >
                    <form id="form">
                        <div class="input-group mb-3">
                        <div class="btn-group me-2"><button type="button" class="btn btn-primary" id="New">New</button> </div>  
                          <div class="btn-toolbar">           
                          </div>  
                        </div>
                    </form>

                    <div class="table-responsive " style="max-height: 300px; overflow: auto">
                        <table class="table table-striped table-hover">
                            <thead>
                                    <tr><th scope="col">Title</th><th scope="col">State</th><th scope="col">Date</th><th scope="col">Contacts</th></tr>
                            </thead>
                            <tbody id="listbody">
                            </tbody>
                        </table>
                    </div> 
                            <div style="max-height: 300px; overflow: auto">
                                    Meeting Details
                                    <div id="detailsBody">
                                    </div>
                             </div>  
                </div>
            </div>
        </div>         
    `;
        return html;
    }

    renderModal = () => {
        const html = `
                    <div id="modal" class="modal fade" tabindex="-1">
           <div class="modal-dialog">
               <div class="modal-content">
                   <div class="modal-header" >
                       <span style='margin-left:4em;font-weight: bold;'id="modalHeaderDescription">New Meeting</span> 
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                   </div>
                        <div class="modal-body" id="modal-body">
                            <h4 class="card-title mt-3 text-center" id="card-modal">Empty</h4>  
                        </div>   
               </div>         
           </div>          
       </div>  
        `;
        return html;
    }

    list = async() => {
        await this.listMeetings();
        var listing = this.dom.querySelector("#listbody");
        listing.innerHTML = "";
        this.state.meetings.forEach(e => this.row(listing, e));
    }

    row = (list, m) => {

        var tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${m.title}</td><td>${m.state}</td><td>${m.date}</td><td>${m.contacts.length}</td>
        `;
        tr.addEventListener('click', e => this.detailsShow(m));
        list.append(tr);
    }
    rowContact = (list, m) => {

        var tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${m.email}</td>
            <br>
        `;
        tr.addEventListener('click', e => this.addComentario(m));
        list.append(tr);
    }

    addComentario = (c) => {
        var comentario = this.dom.querySelector('#Comentario');
        var p = document.createElement("p");
        p.innerHTML = `
            <p>${c.email}</p>
        `;
        comentario.appendChild(p);
    }

    listMeetings = async() => {
        try {
            const requestDireccion = `${backend}/users/meetings`; // Reemplaza "backend" con la URL de tu backend y "/preguntas/all" con el endpoint correcto

            // Realizar la solicitud GET al endpoint de obtención de preguntas en el backend
            const response = await fetch(requestDireccion, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                // Manejar el error de la solicitud
                alert('Error al obtener las preguntas');
                return;
            }

            const meetings = await response.json();

            // Utiliza las preguntas obtenidas para realizar las operaciones necesarias
            console.log(meetings);
            this.state.meetings = meetings;
        } catch (error) {
            console.log('Error:', error);
        }
    }

    detailsShow = (h) => {

        var container = document.createElement("div");

        var title = document.createElement("label");
        title.textContent = "Title : ";
        var titleValue = document.createElement("div");
        titleValue.textContent = h.title;
        container.appendChild(title);
        container.appendChild(titleValue);

        var state = document.createElement("label");
        state.textContent = "State : ";
        var stateValue = document.createElement("div");
        stateValue.textContent = h.state;
        container.appendChild(state);
        container.appendChild(stateValue);


        var date = document.createElement("label");
        date.textContent = "date : ";
        var dateValue = document.createElement("div");
        dateValue.textContent = h.date;
        container.appendChild(date);
        container.appendChild(dateValue);

        var contacts = document.createElement("label");
        contacts.textContent = "contacts : ";
        container.appendChild(contacts);
        h.contacts.forEach(e => this.renderContacts(container, e));


        var detBody = this.dom.querySelector("#detailsBody").replaceChildren(container);

    }

    renderContacts = (container, c) => {
        var contactEmail = document.createElement("div");
        contactEmail.textContent = c.email;
        container.appendChild(contactEmail);
    }

    newMeeting = () => {
        var modalBody = this.dom.querySelector('#modal-body');
        modalBody.replaceChildren();
        modalBody.appendChild(this.createTitleComponent());
        modalBody.appendChild(this.createStateComponent());
        modalBody.appendChild(this.createDateComponent());
        modalBody.appendChild(this.createContactsComponent());

        modalBody.appendChild(this.createButtonElement());
        this.modal.show();
    }

    createContactsComponent = () => {
        const container = document.createElement('div');
        container.classList.add('input-group', 'd-flex', 'w-100', 'contentInfo');

        const span = document.createElement('span');
        span.classList.add('span-expand', 'label');
        span.textContent = 'Possible contacts  :';
        const br = document.createElement('br');
        container.appendChild(this.createComentarioComponent());
        container.appendChild(span);
        container.appendChild(br);
        var contacts = [];
        this.state.meetings.forEach(e => e.contacts.forEach(c => contacts.push(c)));
        let arregloSinRepetidos = this.removeDuplicates(contacts, "email");
        const container2 = document.createElement('div');
        arregloSinRepetidos.forEach(e => this.rowContact(container2, e));
        container.appendChild(container2);
        return container;
    }

    removeDuplicates = (originalArray, prop) => {
        var newArray = [];
        var lookupObject = {};

        for (var i in originalArray) {
            lookupObject[originalArray[i][prop]] = originalArray[i];
        }

        for (i in lookupObject) {
            newArray.push(lookupObject[i]);
        }
        return newArray;
    }

    createComentarioComponent = () => {
        const container = document.createElement('div');
        container.classList.add('input-group', 'd-flex', 'w-100', 'contentInfo');

        const span = document.createElement('span');
        span.classList.add('input-group-text', 'span-expand', 'label');
        span.textContent = 'Definite contacts';

        const textarea = document.createElement('textarea');
        textarea.id = 'Comentario';
        textarea.classList.add('form-control');
        textarea.rows = 5; // Establece el número de filas visibles

        container.appendChild(span);
        container.appendChild(textarea);

        return container;
    }

    createTitleComponent = () => {
        const container = document.createElement('div');
        container.classList.add('input-group', 'd-flex', 'w-100', 'contentInfo');

        const span = document.createElement('span');
        span.classList.add('input-group-text', 'span-expand', 'label');
        span.textContent = 'Title';

        const input = document.createElement('input');
        input.id = 'Title';
        input.type = 'text';
        input.classList.add('input-group-text', 'required');

        container.appendChild(span);
        container.appendChild(input);

        return container;
    }

    createStateComponent = () => {
        const container = document.createElement('div');
        container.classList.add('input-group', 'd-flex', 'w-100', 'contentInfo');

        const span = document.createElement('span');
        span.classList.add('input-group-text', 'span-expand', 'label');
        span.textContent = 'State';

        const input = document.createElement('input');
        input.id = 'State';
        input.type = 'text';
        input.classList.add('input-group-text', 'required');

        container.appendChild(span);
        container.appendChild(input);

        return container;
    }

    createDateComponent = () => {
        const container = document.createElement('div');
        container.classList.add('input-group', 'd-flex', 'w-100', 'contentInfo');

        const span = document.createElement('span');
        span.classList.add('input-group-text', 'span-expand', 'label');
        span.textContent = 'Date';

        const input = document.createElement('input');
        input.id = 'Date';
        input.type = 'text';
        input.classList.add('input-group-text', 'required');

        container.appendChild(span);
        container.appendChild(input);

        return container;
    }

    createButtonElement = () => {
        const button = document.createElement('button');
        button.id = 'Enviar';
        button.type = 'button';
        button.classList.add('btn', 'btn-primary', 'btn-comprar');
        button.textContent = 'Enviar';

        button.addEventListener('click', e => this.sendMeeting());

        return button;
    }

    sendMeeting = async() => {
        var titleValue = this.dom.querySelector('#Title').value;
        var stateValue = this.dom.querySelector('#State').value;
        var dateValue = document.querySelector('#Date').value;

        const meeting = {
            title: titleValue,
            state: stateValue,
            date: dateValue
        };

        var direccion = `${backend}/users/newMeeting`;
        try {
            const response = await fetch(direccion, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(meeting)
            });

            if (!response.ok) {
                // Manejar el error de la solicitud
                alert('Error al crear');
                return;
            }

            this.list();
            this.modal.hide();

        } catch (error) {
            console.log('Error:', error);
        }
    }

}

