import { Component } from '@angular/core';
import { ITEMS_PER_PAGE } from 'src/app/config/pagination.constants';
import { combineLatest } from 'rxjs';
import { GroupDTO } from '../group_permissions.model';
import { GroupService } from '../service/group.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-group_permissions',
  templateUrl: './group_permissions.component.html',
})
export class GroupPermissionsComponent {

  groups: GroupDTO[] | null = null;
  totalItems = 0;
  itemsPerPage: number = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  constructor(
    private groupService: GroupService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
  }

  loadChanged() {
    this.loadAll();
  }

  trackIdentity(_index: number, item: GroupDTO): number {
    return item.id!;
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
      },
    });
  }

  private handleNavigation(): void {
    combineLatest([
      this.activatedRoute.data,
      this.activatedRoute.queryParamMap,
    ]).subscribe(([data, params]) => {
      this.loadAll();
    });
  }

  loadAll(): void {
    this.groupService
      .query()
      .subscribe({
        next: (res: HttpResponse<GroupDTO[]>) => {
          this.onSuccess(res.body, res.headers);
        },
      });
  }

  private onSuccess(groups: GroupDTO[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.groups = groups;
  }

}

