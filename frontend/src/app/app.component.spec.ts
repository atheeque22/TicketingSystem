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
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0
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
          this.status = message;
        } else if (message === 'Simulation completed') {
          this.status = 'Simulation completed successfully.';
        } else {
          this.messages.push(message);
        }
      },
      error: (error) => {
        this.status = `WebSocket error: ${error}`;
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
