import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private webSocket: WebSocket | null = null;
  private messagesSubject: Subject<string> = new Subject<string>();

  constructor() {
    if (typeof window !== 'undefined' && window.WebSocket) {
      this.webSocket = new WebSocket('ws://localhost:8080/ws/config');
    } else {
      console.error("WebSocket is not available in this environment.");
    }

  }

  connect(): void {
    // Establish WebSocket connection if it's not open
    if (!this.webSocket || this.webSocket.readyState !== WebSocket.OPEN) {
      this.webSocket = new WebSocket('ws://localhost:8080/ws/config');

      // Listen for messages from the server
      this.webSocket.onmessage = (event) => {
        this.messagesSubject.next(event.data);
      };

      // Handle errors
      this.webSocket.onerror = (error) => {
        console.error('WebSocket error:', error);
        this.messagesSubject.error('WebSocket connection error.');
      };

      // Handle connection closure
      this.webSocket.onclose = () => {
        console.log('WebSocket connection closed.');
        this.messagesSubject.complete();
      };
    }
  }

  sendConfiguration(config: any): void {
    // Ensure WebSocket connection is open before sending
    if (this.webSocket && this.webSocket.readyState === WebSocket.OPEN) {
      const payload = JSON.stringify(config);
      this.webSocket.send(payload);
    } else {
      console.error('WebSocket connection is not open.');
    }
  }

  getMessages(): Observable<string> {
    return this.messagesSubject.asObservable();
  }
}
