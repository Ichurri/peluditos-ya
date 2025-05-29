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
  selectedPayment: string = '';
  customAmount: number | null = null;

  cardNumber = '';
  expiry = '';
  cvv = '';
  nameOnCard = '';

  paypalEmail = '';

  selectAmount(amount: string) {
    this.selectedAmount = amount;
    if (amount !== 'Otro') {
      this.customAmount = null;
    }
  }

  selectPaymentMethod(method: string) {
    this.selectedPayment = method;
  }

  completeDonation() {
    const amount = this.selectedAmount === 'Otro' ? this.customAmount : this.selectedAmount;

    if (!amount || !this.selectedPayment) {
      alert('Por favor, selecciona un monto y un método de pago.');
      return;
    }

    alert('🎉 ¡Gracias por tu donación!');

    // Reset
    this.selectedAmount = '';
    this.selectedPayment = '';
    this.customAmount = null;
    this.cardNumber = '';
    this.expiry = '';
    this.cvv = '';
    this.nameOnCard = '';
    this.paypalEmail = '';
  }
}

