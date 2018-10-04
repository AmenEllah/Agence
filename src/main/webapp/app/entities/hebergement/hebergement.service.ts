import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHebergement } from 'app/shared/model/hebergement.model';

type EntityResponseType = HttpResponse<IHebergement>;
type EntityArrayResponseType = HttpResponse<IHebergement[]>;

@Injectable({ providedIn: 'root' })
export class HebergementService {
    private resourceUrl = SERVER_API_URL + 'api/hebergements';

    constructor(private http: HttpClient) {}

    create(hebergement: IHebergement): Observable<EntityResponseType> {
        return this.http.post<IHebergement>(this.resourceUrl, hebergement, { observe: 'response' });
    }

    update(hebergement: IHebergement): Observable<EntityResponseType> {
        return this.http.put<IHebergement>(this.resourceUrl, hebergement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHebergement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHebergement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
