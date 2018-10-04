import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Hebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from './hebergement.service';
import { HebergementComponent } from './hebergement.component';
import { HebergementDetailComponent } from './hebergement-detail.component';
import { HebergementUpdateComponent } from './hebergement-update.component';
import { HebergementDeletePopupComponent } from './hebergement-delete-dialog.component';
import { IHebergement } from 'app/shared/model/hebergement.model';

@Injectable({ providedIn: 'root' })
export class HebergementResolve implements Resolve<IHebergement> {
    constructor(private service: HebergementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((hebergement: HttpResponse<Hebergement>) => hebergement.body));
        }
        return of(new Hebergement());
    }
}

export const hebergementRoute: Routes = [
    {
        path: 'hebergement',
        component: HebergementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.hebergement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hebergement/:id/view',
        component: HebergementDetailComponent,
        resolve: {
            hebergement: HebergementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.hebergement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hebergement/new',
        component: HebergementUpdateComponent,
        resolve: {
            hebergement: HebergementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.hebergement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hebergement/:id/edit',
        component: HebergementUpdateComponent,
        resolve: {
            hebergement: HebergementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.hebergement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hebergementPopupRoute: Routes = [
    {
        path: 'hebergement/:id/delete',
        component: HebergementDeletePopupComponent,
        resolve: {
            hebergement: HebergementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.hebergement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
