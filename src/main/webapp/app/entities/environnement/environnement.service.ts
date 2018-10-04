import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnvironnement } from 'app/shared/model/environnement.model';

type EntityResponseType = HttpResponse<IEnvironnement>;
type EntityArrayResponseType = HttpResponse<IEnvironnement[]>;

@Injectable({ providedIn: 'root' })
export class EnvironnementService {
    private resourceUrl = SERVER_API_URL + 'api/environnements';

    constructor(private http: HttpClient) {}

    create(environnement: IEnvironnement): Observable<EntityResponseType> {
        return this.http.post<IEnvironnement>(this.resourceUrl, environnement, { observe: 'response' });
    }

    update(environnement: IEnvironnement): Observable<EntityResponseType> {
        return this.http.put<IEnvironnement>(this.resourceUrl, environnement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEnvironnement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEnvironnement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
