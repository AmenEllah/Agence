import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Rubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from './rubrique.service';
import { RubriqueComponent } from './rubrique.component';
import { RubriqueDetailComponent } from './rubrique-detail.component';
import { RubriqueUpdateComponent } from './rubrique-update.component';
import { RubriqueDeletePopupComponent } from './rubrique-delete-dialog.component';
import { IRubrique } from 'app/shared/model/rubrique.model';

@Injectable({ providedIn: 'root' })
export class RubriqueResolve implements Resolve<IRubrique> {
    constructor(private service: RubriqueService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((rubrique: HttpResponse<Rubrique>) => rubrique.body));
        }
        return of(new Rubrique());
    }
}

export const rubriqueRoute: Routes = [
    {
        path: 'rubrique',
        component: RubriqueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.rubrique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rubrique/:id/view',
        component: RubriqueDetailComponent,
        resolve: {
            rubrique: RubriqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.rubrique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rubrique/new',
        component: RubriqueUpdateComponent,
        resolve: {
            rubrique: RubriqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.rubrique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rubrique/:id/edit',
        component: RubriqueUpdateComponent,
        resolve: {
            rubrique: RubriqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.rubrique.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rubriquePopupRoute: Routes = [
    {
        path: 'rubrique/:id/delete',
        component: RubriqueDeletePopupComponent,
        resolve: {
            rubrique: RubriqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.rubrique.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
