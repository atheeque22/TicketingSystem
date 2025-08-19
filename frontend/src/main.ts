import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';  // Import FormsModule for ngModel

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),  // Provides HTTP client for making API calls
    { provide: FormsModule, useValue: FormsModule }  // Provide FormsModule for ngModel support
  ]
})
  .catch(err => console.error(err));
