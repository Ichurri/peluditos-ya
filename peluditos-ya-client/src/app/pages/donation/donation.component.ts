import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Component } from '@angular/core';

@Component({
  selector: 'app-donation',
  templateUrl: './donation.component.html',
  styleUrls: ['./donation.component.css'],
  imports: [CommonModule, FormsModule],
})
export class DonationComponent {
  selectedAmount: string = '';
  customAmount: string = '';
  selectedPayment: string = 'Tarjeta';

  // Datos de tarjeta (simulados)
  cardNumber: string = '';
  expiry: string = '';
  cvv: string = '';
  nameOnCard: string = '';

  // Método para seleccionar monto
  selectAmount(amount: string) {
    this.selectedAmount = amount;
    this.customAmount = '';
  }

  // Método para seleccionar método de pago
  selectPaymentMethod(method: string) {
    this.selectedPayment = method;
  }

  // Método para completar la donación
  completeDonation() {
    let amount = this.selectedAmount || this.customAmount;
    if (!amount || !this.selectedPayment) {
      alert('Por favor, selecciona un monto y método de pago.');
      return;
    }

    // Aquí podrías llamar a un servicio real, por ahora mostramos mensaje
    alert('¡Donación completada con éxito! Gracias por tu apoyo ❤️');

    // Reset
    this.selectedAmount = '';
    this.customAmount = '';
    this.selectedPayment = 'Tarjeta';
    this.cardNumber = '';
    this.expiry = '';
    this.cvv = '';
    this.nameOnCard = '';
  }
}
