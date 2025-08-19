import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component'; // Standalone component, still included for organization

@NgModule({
  declarations: [
    // You can keep this here for organizational purposes, but it's no longer necessary for bootstrapping
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppComponent,
    // Import necessary modules here, if you plan to use more modules in the future
  ],
  providers: [],
  bootstrap: [] // This line will be replaced by bootstrapApplication in main.ts
})
export class AppModule {}
