import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Confort } from 'app/shared/model/confort.model';
import { ConfortService } from './confort.service';
import { ConfortComponent } from './confort.component';
import { ConfortDetailComponent } from './confort-detail.component';
import { ConfortUpdateComponent } from './confort-update.component';
import { ConfortDeletePopupComponent } from './confort-delete-dialog.component';
import { IConfort } from 'app/shared/model/confort.model';

@Injectable({ providedIn: 'root' })
export class ConfortResolve implements Resolve<IConfort> {
    constructor(private service: ConfortService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((confort: HttpResponse<Confort>) => confort.body));
        }
        return of(new Confort());
    }
}

export const confortRoute: Routes = [
    {
        path: 'confort',
        component: ConfortComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.confort.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'confort/:id/view',
        component: ConfortDetailComponent,
        resolve: {
            confort: ConfortResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.confort.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'confort/new',
        component: ConfortUpdateComponent,
        resolve: {
            confort: ConfortResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.confort.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'confort/:id/edit',
        component: ConfortUpdateComponent,
        resolve: {
            confort: ConfortResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.confort.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const confortPopupRoute: Routes = [
    {
        path: 'confort/:id/delete',
        component: ConfortDeletePopupComponent,
        resolve: {
            confort: ConfortResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.confort.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
