import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  imports: [FormsModule, CommonModule],
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css'],

})
export class ContactUsComponent {
  name = '';
  email = '';
  message = '';

  onSubmit(form: any): void {
    if (form.valid) {
      alert('¡Gracias por contactarnos! Te responderemos pronto.');
      this.clearForm(form);
    }
  }

  clearForm(form: any): void {
    this.name = '';
    this.email = '';
    this.message = '';
    form.resetForm();
  }
}
