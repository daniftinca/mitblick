import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpRequest} from "@angular/common/http";
import {Observable, of} from "rxjs/index";
import {catchError} from "rxjs/internal/operators";
import {MatSnackBar} from "@angular/material";

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService {

  tokenField = 'token';
  errorString: string;
  errorMsg: string;
  constructor(public snackBar: MatSnackBar) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (localStorage.getItem(this.tokenField)) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ` + localStorage.getItem(this.tokenField)
        }
      });
    }

    /**
     * continues request execution
     */
    return next.handle(request).pipe(catchError((error, caught) => {
      // intercept the respons error and displays it to the console
      // console.log(error);;
      this.handleError(error);
      return of(error);
    }) as any);
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, null, {
      duration: 5000,
    })
  }

  private handleError(err: HttpErrorResponse) {
    this.errorString = 'exceptionCodes.' + err.error.value;
    console.log(err);
    //this.translate.get(this.errorString).subscribe((res: string) => this.errorMsg = res);
    this.openSnackBar(this.errorMsg);

  }
}
