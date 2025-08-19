import { Component, OnInit } from '@angular/core';
import { WebSocketService } from './web-socket.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [FormsModule],
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  config = {
    totalTickets: null,
    ticketReleaseRate: null,
    customerRetrievalRate: null,
    maxTicketCapacity: null
  };

  status: string = 'Waiting for configuration...';
  messages: string[] = [];

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit() {
    // Initialize WebSocket connection
    this.webSocketService.connect();

    // Listen for updates from the backend
    this.webSocketService.getMessages().subscribe({
      next: (message) => {
        if (message.startsWith('Error')) {
          this.status = message;  // Display error status
        } else if (message === 'Simulation completed') {
          this.status = 'Simulation completed successfully.';  // Final status
        } else {
          this.messages.push(message);  // Add real-time updates to the messages list
        }
      },
      error: (error) => {
        this.status = `WebSocket error: ${error}`;  // Show WebSocket error
      }
    });
  }


  startThreads() {
    // Send configuration to the backend via WebSocket
    this.webSocketService.sendConfiguration(this.config);

    // Update status
    this.status = 'Simulation started. Waiting for updates...';
  }
}
