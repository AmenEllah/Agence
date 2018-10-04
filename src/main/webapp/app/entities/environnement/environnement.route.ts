import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Environnement } from 'app/shared/model/environnement.model';
import { EnvironnementService } from './environnement.service';
import { EnvironnementComponent } from './environnement.component';
import { EnvironnementDetailComponent } from './environnement-detail.component';
import { EnvironnementUpdateComponent } from './environnement-update.component';
import { EnvironnementDeletePopupComponent } from './environnement-delete-dialog.component';
import { IEnvironnement } from 'app/shared/model/environnement.model';

@Injectable({ providedIn: 'root' })
export class EnvironnementResolve implements Resolve<IEnvironnement> {
    constructor(private service: EnvironnementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((environnement: HttpResponse<Environnement>) => environnement.body));
        }
        return of(new Environnement());
    }
}

export const environnementRoute: Routes = [
    {
        path: 'environnement',
        component: EnvironnementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.environnement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'environnement/:id/view',
        component: EnvironnementDetailComponent,
        resolve: {
            environnement: EnvironnementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.environnement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'environnement/new',
        component: EnvironnementUpdateComponent,
        resolve: {
            environnement: EnvironnementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.environnement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'environnement/:id/edit',
        component: EnvironnementUpdateComponent,
        resolve: {
            environnement: EnvironnementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.environnement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const environnementPopupRoute: Routes = [
    {
        path: 'environnement/:id/delete',
        component: EnvironnementDeletePopupComponent,
        resolve: {
            environnement: EnvironnementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.environnement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
