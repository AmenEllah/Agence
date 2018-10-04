import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Voyage } from 'app/shared/model/voyage.model';
import { VoyageService } from './voyage.service';
import { VoyageComponent } from './voyage.component';
import { VoyageDetailComponent } from './voyage-detail.component';
import { VoyageUpdateComponent } from './voyage-update.component';
import { VoyageDeletePopupComponent } from './voyage-delete-dialog.component';
import { IVoyage } from 'app/shared/model/voyage.model';

@Injectable({ providedIn: 'root' })
export class VoyageResolve implements Resolve<IVoyage> {
    constructor(private service: VoyageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((voyage: HttpResponse<Voyage>) => voyage.body));
        }
        return of(new Voyage());
    }
}

export const voyageRoute: Routes = [
    {
        path: 'voyage',
        component: VoyageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.voyage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voyage/:id/view',
        component: VoyageDetailComponent,
        resolve: {
            voyage: VoyageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.voyage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voyage/new',
        component: VoyageUpdateComponent,
        resolve: {
            voyage: VoyageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.voyage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voyage/:id/edit',
        component: VoyageUpdateComponent,
        resolve: {
            voyage: VoyageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.voyage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voyagePopupRoute: Routes = [
    {
        path: 'voyage/:id/delete',
        component: VoyageDeletePopupComponent,
        resolve: {
            voyage: VoyageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'agenceApp.voyage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
